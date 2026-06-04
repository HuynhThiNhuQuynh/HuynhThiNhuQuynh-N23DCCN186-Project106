package com.csdlpt.publicnode.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SecureFragmentRequestDTO {
    private Integer oid;
    private String name;
    private String ssn;
    private String creditCard;
}
