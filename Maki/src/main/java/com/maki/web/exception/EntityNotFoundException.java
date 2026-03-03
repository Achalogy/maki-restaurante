package com.maki.web.exception;

public class EntityNotFoundException extends RepositoryException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
