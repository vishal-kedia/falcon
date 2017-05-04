package com.kedialabs.session;

import com.kedialabs.user.UserType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSessionDetails {
    private String sessionId;
    private String name;
    private Long contractorId;
    private Long projectId;
    private UserType userType;
    
}
