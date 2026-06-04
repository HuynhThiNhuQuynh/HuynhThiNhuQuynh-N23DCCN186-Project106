package com.csdlpt.publicnode.original;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDataRepository
        extends JpaRepository<CustomerData, Integer> {
}
