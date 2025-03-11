package com.trendy.login;

public class UserDTO {
    private final String email;
    private final String name;
    private final String profileImage;

    public UserDTO(String email, String name, String profileImage) {
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getProfileImage() {
        return profileImage;
    }
}