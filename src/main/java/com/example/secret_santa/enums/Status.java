package com.example.secret_santa.enums;

public enum Status {
    ACTIVE("User account is active"), // Default
    BLOCKED("User account is blocked");

    private final String displayValue;

    private Status(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}