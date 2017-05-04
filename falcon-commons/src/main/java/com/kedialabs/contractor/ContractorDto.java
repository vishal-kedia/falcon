package com.kedialabs.contractor;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class ContractorDto {
    @NotEmpty
    private String name;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String pinCode;
    private String phoneNo; 
}
