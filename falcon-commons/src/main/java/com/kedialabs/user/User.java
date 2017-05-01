package com.kedialabs.user;

import lombok.Data;

@Data
public class User {
    private String userName;
    private String name;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String pinCode;
    private String phoneNo;
    private UserType userType;
}
