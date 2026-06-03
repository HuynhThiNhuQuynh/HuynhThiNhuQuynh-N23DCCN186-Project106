package com.csdlpt.publicnode.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "public_fragment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublicFragment {

    @Id
    private String encOid;

    private String purchaseHistory;
}