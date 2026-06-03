package com.csdlpt.securenode.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "secure_fragment")
@Getter
@Setter
public class SecureFragment {

    @Id
    private Integer oid;

    private String name;

    private String ssn;

    private String creditCard;
}