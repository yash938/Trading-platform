package com.tradingplatform.controller;

import com.tradingplatform.domain.VerificationType;
import com.tradingplatform.dto.HandlerDto;
import com.tradingplatform.dto.PageResponse;
import com.tradingplatform.dto.UserDto;
import com.tradingplatform.entity.User;
import com.tradingplatform.entity.VerificationCode;
import com.tradingplatform.service.EmailService;
import com.tradingplatform.service.UserService;
import com.tradingplatform.service.VerificationService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private EmailService emailService;

  @PostMapping("/signup")
    public ResponseEntity<UserDto> signupUser(@RequestBody UserDto userDto){
      UserDto createUser = userService.createUser(userDto);
      return new ResponseEntity<>(createUser, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDto> updateUser(@PathVariable int id,@RequestBody UserDto userDto){
      UserDto update = userService.udpateUser(id, userDto);
      return new ResponseEntity<>(update,HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> singleUserInfo(@PathVariable int id){
      UserDto user = userService.singleUser(id);
      return new ResponseEntity<>(user,HttpStatus.OK);
  }

  @GetMapping("/allUser")
  public ResponseEntity<PageResponse<UserDto>> allUsers(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                        @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize,
                                                        @RequestParam(value = "sortBy", defaultValue = "fullName", required = false) String sortBy,
                                                        @RequestParam(value = "sortDir", defaultValue = "ASC", required = false) String sortDir){
      PageResponse<UserDto> userDtoPageResponse = userService.allUser(pageNumber, pageSize, sortBy, sortDir);
      return new ResponseEntity<>(userDtoPageResponse,HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HandlerDto> deleteUser(@PathVariable int id){
      userService.deleteUser(id);
      HandlerDto userIsDeleted = HandlerDto.builder().message("User is deleted").status(HttpStatus.OK).success(true).date(LocalDate.now()).build();
      return new ResponseEntity<>(userIsDeleted,HttpStatus.OK);
  }

    @PostMapping("/verificationType/{verificationType}/send-otp}")
    public ResponseEntity<String> sendVerificationType(@PathVariable VerificationType verificationType) throws MessagingException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        ////////////////////////////////////////////////////
        VerificationCode verificationByUser = verificationService.getVerificationByUser(user.getId());
        if(verificationByUser != null){
            verificationByUser= verificationService.sendVerificationCode(user, verificationType);
        }

        if(verificationType.equals(VerificationType.EMAIL)){
            emailService.sendVerification(user.getEmail(),verificationByUser.getOtp());
        }


        return new ResponseEntity<>("Otp Successfully send",HttpStatus.OK);
    }
  @PutMapping("/enable-two-factor/verify-otp/{otp}")
  public ResponseEntity<User> twoFactorAuth(@PathVariable String otp) throws Exception {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      User user = (User) authentication.getPrincipal();

      VerificationCode verificationCode = verificationService.getVerificationByUser(user.getId());
      String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL) ? verificationCode.getEmail() : verificationCode.getMobile();
      boolean isVarified = verificationCode.getOtp().equals(otp);

      if(isVarified){
          User updateUsers = userService.enableTwoFactorAuth(verificationCode.getVerificationType(), sendTo, user);
          verificationService.verificationCodeDelete(verificationCode.getId());
          return new ResponseEntity<>(updateUsers,HttpStatus.OK);
      }


      throw new Exception("Wrong otp");
  }


}
