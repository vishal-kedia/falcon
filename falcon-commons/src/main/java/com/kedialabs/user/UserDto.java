package com.kedialabs.user;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserDto {
    @NotEmpty
    private String userName;
    private String name;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String pinCode;
    private String phoneNo;
    private String password;
    @NotEmpty
    private UserType userType;
}
