package com.visualpathit.userservice.service;

import com.visualpathit.common.dto.RegisterUserRequest;
import com.visualpathit.common.dto.UserDto;
import com.visualpathit.userservice.mapper.UserMapper;
import com.visualpathit.userservice.model.User;
import com.visualpathit.userservice.repository.RoleRepository;
import com.visualpathit.userservice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userRepository.findAllWithRoles().stream().map(UserMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public UserDto findById(long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Transactional(readOnly = true)
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return UserMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    public User findEntityByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return user;
    }

    @Transactional
    public UserDto register(RegisterUserRequest request) {
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }
        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserEmail(request.getUserEmail());
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        return UserMapper.toDto(userRepository.save(user));
    }

    @Transactional
    public UserDto save(UserDto dto) {
        User user = userRepository.findByUsername(dto.getUsername());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        UserMapper.updateProfile(user, dto);
        if (dto.getProfileImg() != null) {
            user.setProfileImg(dto.getProfileImg());
        }
        if (dto.getProfileImgPath() != null) {
            user.setProfileImgPath(dto.getProfileImgPath());
        }
        return UserMapper.toDto(userRepository.save(user));
    }

    @Transactional
    public UserDto updateProfile(String username, UserDto dto) {
        User user = findEntityByUsername(username);
        UserMapper.updateProfile(user, dto);
        return UserMapper.toDto(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public boolean authenticate(String username, String rawPassword) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false;
        }
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}
