package com.csdlpt.securenode.controller;

import com.csdlpt.securenode.entity.SecureFragment;
import com.csdlpt.securenode.repository.SecureFragmentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/secure")
public class SecureFragmentController {

    private final SecureFragmentRepository repository;

    public SecureFragmentController(SecureFragmentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/all")
    public List<SecureFragment> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{oid}")
    public SecureFragment getByOid(@PathVariable Integer oid) {
        return repository.findByOid(oid).orElse(null);
    }
}