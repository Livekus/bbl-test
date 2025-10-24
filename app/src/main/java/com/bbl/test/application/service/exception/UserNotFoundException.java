package com.bbl.test.application.service.exception;

public class UserNotFoundException extends RuntimeException {
  private final Long id;
  public UserNotFoundException(Long id) {
    super("User %d not found".formatted(id));
    this.id = id;
  }
  public Long id() { return id; }
}

