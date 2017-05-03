package com.kedialabs.user;

import lombok.Data;

@Data
public class UserDto {
    private String userName;
    private String name;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String pinCode;
    private String phoneNo;
    private String password;
    private UserType userType;
}
