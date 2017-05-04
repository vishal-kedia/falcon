package com.kedialabs.session;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class UserLoginRequestDto {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;
}
