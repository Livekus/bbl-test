package com.bbl.test.adapter.backend.controller;

import com.bbl.test.adapter.backend.dtos.CreateUserRequest;
import com.bbl.test.adapter.backend.dtos.UpdateUserRequest;
import com.bbl.test.domain.model.User;
import com.bbl.test.application.service.exception.UserNotFoundException;
import com.bbl.test.application.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService users;

  public UserController(UserService users) { this.users = users; }

  @GetMapping
  public List<User> getAll() { return users.findAll(); }

  @GetMapping("/{id}")
  public User getById(@PathVariable Long id) {
    return users.findById(id).orElseThrow(() -> new UserNotFoundException(id));
  }

  @PostMapping
  public ResponseEntity<User> create(@RequestBody @Valid CreateUserRequest request) {
    User created = users.create(request);
    return ResponseEntity.created(URI.create("/users/" + created.id())).body(created);
  }

  @PutMapping("/{id}")
  public ResponseEntity<User> update(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest request) {
    User updated = users.update(id, request);
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    users.delete(id);
    return ResponseEntity.noContent().build();
  }
}