package com.ppr.java.effectivemobileproject.service;

import com.ppr.java.effectivemobileproject.dto.PageRequestDto;
import com.ppr.java.effectivemobileproject.dto.PageResponseDto;
import com.ppr.java.effectivemobileproject.dto.user.UserDto;
import com.ppr.java.effectivemobileproject.model.User;
import java.util.Optional;

public interface UserService {
    UserDto create(UserDto userDto);
    UserDto updatePhone(Long id,String newPhone);
    UserDto updateEmail(Long id,String email);
    UserDto findById(Long id);
    UserDto deletePhone(Long id,String phone);
    UserDto deleteEmail(Long id,String email);
    PageResponseDto<User> search(PageRequestDto userFilter);
    Optional<User> findEntityById(Long id);




}
