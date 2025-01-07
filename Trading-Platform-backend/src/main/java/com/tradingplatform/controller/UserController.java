package com.tradingplatform.controller;

import com.tradingplatform.domain.VerificationType;
import com.tradingplatform.dto.*;
import com.tradingplatform.entity.ResetPassword;
import com.tradingplatform.entity.User;
import com.tradingplatform.entity.VerificationCode;
import com.tradingplatform.service.EmailService;
import com.tradingplatform.service.ForgotPasswordService;
import com.tradingplatform.service.UserService;
import com.tradingplatform.service.VerificationService;
import com.tradingplatform.utils.OtpUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationService verificationService;
    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signupUser(@RequestBody UserDto userDto) {
        UserDto createUser = userService.createUser(userDto);
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable int id, @RequestBody UserDto userDto) {
        UserDto update = userService.udpateUser(id, userDto);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> singleUserInfo(@PathVariable int id) {
        UserDto user = userService.singleUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/allUser")
    public ResponseEntity<PageResponse<UserDto>> allUsers(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                          @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize,
                                                          @RequestParam(value = "sortBy", defaultValue = "fullName", required = false) String sortBy,
                                                          @RequestParam(value = "sortDir", defaultValue = "ASC", required = false) String sortDir) {
        PageResponse<UserDto> userDtoPageResponse = userService.allUser(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(userDtoPageResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HandlerDto> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        HandlerDto userIsDeleted = HandlerDto.builder().message("User is deleted").status(HttpStatus.OK).success(true).date(LocalDate.now()).build();
        return new ResponseEntity<>(userIsDeleted, HttpStatus.OK);
    }

    @PostMapping("/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOtp(@RequestBody ForgotPasswordRequest request) throws MessagingException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User) authentication.getPrincipal();
        User user = userService.getByEmail(request.getSendTo());

        String otp = OtpUtils.generateOtp();
        String id = UUID.randomUUID().toString();
        ResetPassword token = forgotPasswordService.findByUser(user.getId());
        if (token == null) {
            token = forgotPasswordService.createToken(user, id, otp, request.getVerificationType(), request.getSendTo());
        }

        if (request.getVerificationType().equals(VerificationType.EMAIL)) {
            emailService.sendVerification(user.getEmail(), token.getOtp());
        }

        AuthResponse reset = AuthResponse.builder().session(token.getId()).message("password reset otp sent successfullly").build();


        return new ResponseEntity<>(reset, HttpStatus.OK);
    }

    @PutMapping("/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> twoFactorAuth(@PathVariable String otp) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        VerificationCode verificationCode = verificationService.getVerificationByUser(user.getId());
        String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL) ? verificationCode.getEmail() : verificationCode.getMobile();
        boolean isVarified = verificationCode.getOtp().equals(otp);

        if (isVarified) {
            User updateUsers = userService.enableTwoFactorAuth(verificationCode.getVerificationType(), sendTo, user);
            verificationService.verificationCodeDelete(verificationCode.getId());
            return new ResponseEntity<>(updateUsers, HttpStatus.OK);
        }


        throw new Exception("Wrong otp");
    }

    @PutMapping("/reset-password/verifyOtp")
    public ResponseEntity<ApiResponse> resetPassword(@PathVariable int id, @RequestBody ResetPasswordRequest request) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        ResetPassword reset = forgotPasswordService.findById(id);
        boolean isVerified = reset.getOtp().equals(request.getOtp());
        if (isVerified) {
            userService.updatePassword(reset.getUser(), reset.getUser().getPassword());
            ApiResponse password = ApiResponse.builder().message("password updated successfully").build();
            return new ResponseEntity<>(password, HttpStatus.OK);
        }

        throw new RuntimeException("Wrong otp");
    }

}
