package com.tradingplatform.ServiceImpementation;

import com.tradingplatform.Exception.ResourceNotFound;
import com.tradingplatform.dto.PageResponse;
import com.tradingplatform.dto.UserDto;
import com.tradingplatform.entity.User;
import com.tradingplatform.repository.UserRepo;
import com.tradingplatform.service.UserService;
import com.tradingplatform.utils.PaginationHelper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j

public class UserImpl implements UserService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User createUser = userRepo.save(user);
        log.info("Created User {}",createUser);
        UserDto savedUser = modelMapper.map(createUser, UserDto.class);
        return savedUser;
    }

    @Override
    public UserDto udpateUser(int id, UserDto userDto) {
        User userId = userRepo.findById(id).orElseThrow(() -> new ResourceNotFound("User is not found"));
        userId.setEmail(userDto.getEmail());
        userId.setFullName(userDto.getFullName());
        userId.setPassword(userDto.getPassword());
        userId.setRole(userDto.getRole());
        userId.setTwoFactorAuth(userDto.getTwoFactorAuth());
        User updateUser = userRepo.save(userId);
        return modelMapper.map(updateUser, UserDto.class);
    }

    @Override
    public PageResponse<UserDto> allUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> allUsers = userRepo.findAll(pageRequest);
        PageResponse<UserDto> pages = PaginationHelper.getPages(allUsers, UserDto.class);
        return pages;
    }

    @Override
    public UserDto singleUser(int id) {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFound("User not found"));
        log.info("User id is {}",user);
        return modelMapper.map(user, UserDto.class);

    }

    @Override
    public void deleteUser(int id) {
        User userId = userRepo.findById(id).orElseThrow(() -> new ResourceNotFound("User not found"));
        userRepo.delete(userId);
        log.info("User is deleted with given id {}",userId);
    }

    @Override
    public User getByEmail(String email) {
        UserDto userEmail = userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFound("user is not found with given email"));
        User user = modelMapper.map(userEmail, User.class);
        return user;
    }
}
