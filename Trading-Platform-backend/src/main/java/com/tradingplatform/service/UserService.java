package com.tradingplatform.service;


import com.tradingplatform.domain.VerificationType;
import com.tradingplatform.dto.PageResponse;
import com.tradingplatform.dto.UserDto;
import com.tradingplatform.entity.User;

public interface UserService {

        public UserDto createUser(UserDto userDto);
        public UserDto udpateUser(int id,UserDto userDto);
        public PageResponse<UserDto> allUser(int pageNumber, int pageSize, String sortBy, String sortDir);
        public UserDto singleUser(int id);
        public void deleteUser(int id);
        public User getByEmail(String email);
        public User enableTwoFactorAuth(VerificationType verificationType,String sendTo, User user);

        User updatePassword(User user,String newPassword);

}
