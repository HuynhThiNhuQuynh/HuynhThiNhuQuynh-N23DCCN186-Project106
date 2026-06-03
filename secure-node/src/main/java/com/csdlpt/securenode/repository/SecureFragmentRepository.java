package com.csdlpt.securenode.repository;

import com.csdlpt.securenode.entity.SecureFragment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecureFragmentRepository
        extends JpaRepository<SecureFragment, Integer> {

    Optional<SecureFragment> findByOid(Integer oid);
}