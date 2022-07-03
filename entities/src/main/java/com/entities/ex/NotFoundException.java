package com.entities.ex;

public class NotFoundException extends RuntimeException {

    private Long objIdentifier;

    public <T> NotFoundException(Class<T> cls, Long id) {
        super(cls.getSimpleName() + " with id: " + id + " does not exist!");
    }

    public Long getObjIdentifier() {
        return objIdentifier;
    }
}
