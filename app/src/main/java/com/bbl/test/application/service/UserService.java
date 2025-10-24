package com.bbl.test.application.service;

import com.bbl.test.adapter.backend.dtos.CreateUserRequest;
import com.bbl.test.adapter.backend.dtos.UpdateUserRequest;
import com.example.userservice.domain.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    User create(CreateUserRequest req);
    User update(Long id, UpdateUserRequest req);
    void delete(Long id);
}