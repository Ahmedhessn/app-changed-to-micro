package com.visualpathit.common.dto;

import java.io.Serializable;
import java.util.Set;

public class UserDto implements Serializable {

    private Long id;
    private String username;
    private String userEmail;
    private String dateOfBirth;
    private String fatherName;
    private String motherName;
    private String gender;
    private String maritalStatus;
    private String permanentAddress;
    private String tempAddress;
    private String primaryOccupation;
    private String secondaryOccupation;
    private String skills;
    private String phoneNumber;
    private String secondaryPhoneNumber;
    private String nationality;
    private String language;
    private String workingExperience;
    private String profileImg;
    private String profileImgPath;
    private Set<String> roles;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getFatherName() { return fatherName; }
    public void setFatherName(String fatherName) { this.fatherName = fatherName; }
    public String getMotherName() { return motherName; }
    public void setMotherName(String motherName) { this.motherName = motherName; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getMaritalStatus() { return maritalStatus; }
    public void setMaritalStatus(String maritalStatus) { this.maritalStatus = maritalStatus; }
    public String getPermanentAddress() { return permanentAddress; }
    public void setPermanentAddress(String permanentAddress) { this.permanentAddress = permanentAddress; }
    public String getTempAddress() { return tempAddress; }
    public void setTempAddress(String tempAddress) { this.tempAddress = tempAddress; }
    public String getPrimaryOccupation() { return primaryOccupation; }
    public void setPrimaryOccupation(String primaryOccupation) { this.primaryOccupation = primaryOccupation; }
    public String getSecondaryOccupation() { return secondaryOccupation; }
    public void setSecondaryOccupation(String secondaryOccupation) { this.secondaryOccupation = secondaryOccupation; }
    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getSecondaryPhoneNumber() { return secondaryPhoneNumber; }
    public void setSecondaryPhoneNumber(String secondaryPhoneNumber) { this.secondaryPhoneNumber = secondaryPhoneNumber; }
    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getWorkingExperience() { return workingExperience; }
    public void setWorkingExperience(String workingExperience) { this.workingExperience = workingExperience; }
    public String getProfileImg() { return profileImg; }
    public void setProfileImg(String profileImg) { this.profileImg = profileImg; }
    public String getProfileImgPath() { return profileImgPath; }
    public void setProfileImgPath(String profileImgPath) { this.profileImgPath = profileImgPath; }
    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
}
