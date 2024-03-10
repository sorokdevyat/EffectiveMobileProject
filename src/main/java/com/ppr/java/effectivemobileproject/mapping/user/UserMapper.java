package com.ppr.java.effectivemobileproject.mapping.user;

import com.ppr.java.effectivemobileproject.dto.user.UserDto;
import com.ppr.java.effectivemobileproject.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User fromDtoToUser(UserDto userDto){
        return User.builder()
                .id(userDto.getId())
                .fullname(userDto.getFullname())
                .phoneNumbers(userDto.getPhoneNumbers())
                .emails(userDto.getEmails())
                .dateOfBirth(userDto.getDateOfBirth())
                .build();
    }
    public UserDto fromUserToUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .fullname(user.getFullname())
                .phoneNumbers(user.getPhoneNumbers())
                .emails(user.getEmails())
                .dateOfBirth(user.getDateOfBirth())
                .build();
    }
}
