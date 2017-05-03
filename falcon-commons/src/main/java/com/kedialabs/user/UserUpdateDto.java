package com.kedialabs.user;

import lombok.Data;

@Data
public class UserUpdateDto {
    private String name;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String pinCode;
    private String phoneNo;
    private UserType userType;
}
