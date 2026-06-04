package com.csdlpt.publicnode.original;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer_data", schema = "original_db")
@Getter
@Setter
public class CustomerData {

    @Id
    private Integer oid;

    private String name;

    private String ssn;

    @Column(name = "credit_card")
    private String creditCard;

    @Column(name = "purchase_history")
    private String purchaseHistory;
}