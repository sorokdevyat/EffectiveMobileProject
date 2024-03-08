package com.ppr.java.effectivemobileproject.contoller;

import com.ppr.java.effectivemobileproject.dto.PageRequestDto;
import com.ppr.java.effectivemobileproject.dto.PageResponseDto;
import com.ppr.java.effectivemobileproject.dto.account.BankAccountDto;

import com.ppr.java.effectivemobileproject.dto.user.UserDto;
import com.ppr.java.effectivemobileproject.model.User;
import com.ppr.java.effectivemobileproject.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/emproject/users")
@Tag(name = "user",description = "User Controller")
public class UserController {
    private final UserService userService;
    @PostMapping("/search")
    public PageResponseDto<User> search(@RequestBody PageRequestDto pageRequestDto){
        return userService.search(pageRequestDto);
    }
    @PostMapping("/create")
    public UserDto create(@RequestBody UserDto userDto){
        return userService.create(userDto);
    }
    @PutMapping("/addPhone/{userId}")
    public UserDto addPhone(@PathVariable Long userId,@RequestParam String phone){
        return userService.updatePhone(userId,phone);
    }

    @PutMapping("/addEmail/{userId}")
    public UserDto addEmail(@PathVariable Long userId,@RequestParam String email){
        return userService.updateEmail(userId,email);
    }
    @PutMapping("/deletePhone/{userId}")
    public UserDto deletePhone(@PathVariable Long userId,@RequestParam String phone){
        return userService.deletePhone(userId,phone);
    }
    @PutMapping("/deleteEmail/{userId}")
    public UserDto deleteEmail(@PathVariable Long userId,@RequestParam String email){
        return userService.deleteEmail(userId,email);
    }

}
