package com.visualpathit.userservice.mapper;

import com.visualpathit.common.dto.UserDto;
import com.visualpathit.userservice.model.Role;
import com.visualpathit.userservice.model.User;

import java.util.stream.Collectors;

public final class UserMapper {

    private UserMapper() {}

    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setUserEmail(user.getUserEmail());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setFatherName(user.getFatherName());
        dto.setMotherName(user.getMotherName());
        dto.setGender(user.getGender());
        dto.setMaritalStatus(user.getMaritalStatus());
        dto.setPermanentAddress(user.getPermanentAddress());
        dto.setTempAddress(user.getTempAddress());
        dto.setPrimaryOccupation(user.getPrimaryOccupation());
        dto.setSecondaryOccupation(user.getSecondaryOccupation());
        dto.setSkills(user.getSkills());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setSecondaryPhoneNumber(user.getSecondaryPhoneNumber());
        dto.setNationality(user.getNationality());
        dto.setLanguage(user.getLanguage());
        dto.setWorkingExperience(user.getWorkingExperience());
        dto.setProfileImg(user.getProfileImg());
        dto.setProfileImgPath(user.getProfileImgPath());
        if (user.getRoles() != null) {
            dto.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
        }
        return dto;
    }

    public static void updateProfile(User user, UserDto dto) {
        user.setUsername(dto.getUsername());
        user.setUserEmail(dto.getUserEmail());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setFatherName(dto.getFatherName());
        user.setMotherName(dto.getMotherName());
        user.setGender(dto.getGender());
        user.setLanguage(dto.getLanguage());
        user.setMaritalStatus(dto.getMaritalStatus());
        user.setNationality(dto.getNationality());
        user.setPermanentAddress(dto.getPermanentAddress());
        user.setTempAddress(dto.getTempAddress());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setSecondaryPhoneNumber(dto.getSecondaryPhoneNumber());
        user.setPrimaryOccupation(dto.getPrimaryOccupation());
        user.setSecondaryOccupation(dto.getSecondaryOccupation());
        user.setSkills(dto.getSkills());
        user.setWorkingExperience(dto.getWorkingExperience());
    }
}
