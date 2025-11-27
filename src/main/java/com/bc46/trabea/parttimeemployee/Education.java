package com.bc46.trabea.parttimeemployee;

public enum Education {
    ES("Elementary School"),
    JHS("Junior High School"),
    HS("High School"),
    BCH("Bachelor"),
    MST("Master"),
    PHD("Doctorate");

    private String description;

    Education(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
