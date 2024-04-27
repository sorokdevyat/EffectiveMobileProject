package com.ppr.java.effectivemobileproject.service.jpa;

import com.ppr.java.effectivemobileproject.dto.PageRequestDto;
import com.ppr.java.effectivemobileproject.dto.PageResponseDto;
import com.ppr.java.effectivemobileproject.dto.user.UserDto;
import com.ppr.java.effectivemobileproject.mapping.user.UserMapper;
import com.ppr.java.effectivemobileproject.model.User;
import com.ppr.java.effectivemobileproject.model.exceptions.NotUniqueEmailException;
import com.ppr.java.effectivemobileproject.model.exceptions.NotUniquePhoneException;
import com.ppr.java.effectivemobileproject.repository.UserRepository;
import com.ppr.java.effectivemobileproject.service.UserService;
import com.ppr.java.effectivemobileproject.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceJpa implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public PageResponseDto<User> search(PageRequestDto pageRequestDto) {
        List<User> pageOfUsers = userRepository.findAll(UserSpecification.getSpec(pageRequestDto.getData()),
                PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getSize())).getContent();

        return PageResponseDto.<User>builder()
                .page(pageRequestDto.getPage())
                .total(pageOfUsers.size())
                .responsePage(pageOfUsers)
                .build();
    }
    @Override
    @Transactional
    public UserDto create(UserDto dto) {
        User user = userMapper.fromDtoToUser(dto);
        User createdUser = userRepository.save(user);
        log.info("Create  user with id {}",createdUser.getId());
        return userMapper.fromUserToUserDto(createdUser);
    }
    @Override
    @Transactional
    public UserDto updatePhone(Long id, String phone) {
        if (userRepository.isPhoneRegistered(phone)) {
            throw new NotUniquePhoneException(String.format("Phone number = %s already exist", phone));
        }
        UserDto byId = findById(id);
        byId.getPhoneNumbers().add(phone);
        User updated = userRepository.save(userMapper.fromDtoToUser(byId));
        log.info("Phone {} added to user with id {}",phone,id);
        return userMapper.fromUserToUserDto(updated);
    }
    @Override
    @Transactional
    public UserDto updateEmail(Long id, String email) {
        if (userRepository.isEmailRegistered(email)) {
            throw new NotUniqueEmailException(String.format("Email = %s already exist", email));
        }
        UserDto byId = findById(id);
        byId.getEmails().add(email);
        User updated = userRepository.save(userMapper.fromDtoToUser(byId));
        log.info("Email {} added to user with id {}",email,id);
        return userMapper.fromUserToUserDto(updated);
    }
    @Override
    public UserDto deletePhone(Long id, String phone) {
        UserDto userDto = findById(id);
        Set<String> phones = userDto.getPhoneNumbers();
        if (phones.size() > 1) {
            phones.remove(phone);
        }
        User updated = userRepository.save(userMapper.fromDtoToUser(userDto));
        log.info("Phone {} deleted from user with id {}",phone,id);
        return userMapper.fromUserToUserDto(updated);
    }
    @Override
    public UserDto deleteEmail(Long id, String email) {
        UserDto userDto = findById(id);
        Set<String> emails = userDto.getEmails();
        if (emails.size() > 1) {
            emails.remove(email);
        }
        User updated = userRepository.save(userMapper.fromDtoToUser(userDto));
        log.info("Email {} deleted from user with id {}",email,id);
        return userMapper.fromUserToUserDto(updated);
    }
    @Override
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElse(new User());
        return userMapper.fromUserToUserDto(user);
    }
    public Optional<User> findEntityById(Long id){
        return userRepository.findById(id);
    }
}
