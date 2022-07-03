package com.entities.ex;

public class InvalidCriteriaException extends RuntimeException {

    private String fieldName;

    /**
     * Field that contains an internationalization key for exceptions thrown in service classes
     */
    private String messageKey;

    public InvalidCriteriaException(String fieldName, String messageKey) {
        this.fieldName = fieldName;
        this.messageKey = messageKey;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
