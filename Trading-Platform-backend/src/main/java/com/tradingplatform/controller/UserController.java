package com.tradingplatform.controller;

import com.tradingplatform.dto.HandlerDto;
import com.tradingplatform.dto.PageResponse;
import com.tradingplatform.dto.UserDto;
import com.tradingplatform.entity.User;
import com.tradingplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

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
}
