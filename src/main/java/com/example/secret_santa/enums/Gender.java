package com.example.secret_santa.enums;

public enum Gender {
    MALE("It's male!"), // 0
    FEMALE("It's female!"),
    NB("It's NB person!");

    private final String displayValue;

    private Gender(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}