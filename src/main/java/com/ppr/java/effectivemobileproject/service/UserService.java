package com.ppr.java.effectivemobileproject.service;

import com.ppr.java.effectivemobileproject.dto.PageRequestDto;
import com.ppr.java.effectivemobileproject.dto.PageResponseDto;
import com.ppr.java.effectivemobileproject.dto.account.BankAccountDto;
import com.ppr.java.effectivemobileproject.dto.user.UserDto;
import com.ppr.java.effectivemobileproject.model.User;

import java.util.List;
import java.util.Optional;

//Пользователь может добавить/сменить свои номер телефона и/или email, если они еще не заняты другими пользователями.
//Пользователь может удалить свои телефон и/или email. При этом нельзя удалить ?последний.

public interface UserService {
    UserDto create(UserDto userDto);

    // Как сделать чтобы всё кроме email, phone нельзя было изменить
    // UserDto update(UserDto userDto);

    UserDto updatePhone(Long id,String newPhone);
    UserDto updateEmail(Long id,String email);
    UserDto findById(Long id);
    UserDto deletePhone(Long id,String phone);
    UserDto deleteEmail(Long id,String email);
    PageResponseDto<User> search(PageRequestDto userFilter);
    Optional<User> findEntityById(Long id);




}
