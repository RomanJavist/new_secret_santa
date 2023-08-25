package com.example.secret_santa.enums;

public enum Role {
    USER("Just a simple user"), // Default
    ADMIN("Ooo! It's great person - Admin!");

    private final String displayValue;

    private Role(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}