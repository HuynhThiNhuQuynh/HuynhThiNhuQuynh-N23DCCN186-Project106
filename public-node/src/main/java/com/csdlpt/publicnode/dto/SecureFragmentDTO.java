package com.csdlpt.publicnode.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecureFragmentDTO {

    private Integer oid;
    private String name;
    private String ssn;
    private String creditCard;
}