package com.example.Backend.exception;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

/*
  To throw an exception:
   if (id == null || id.isEmpty()) {
            throw new ResourceNotFoundException("Resume with the given ID not found.");
        }
 */