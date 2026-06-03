package com.csdlpt.publicnode.repository;

import com.csdlpt.publicnode.entity.PublicFragment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PublicFragmentRepository
        extends JpaRepository<PublicFragment, String> {
}