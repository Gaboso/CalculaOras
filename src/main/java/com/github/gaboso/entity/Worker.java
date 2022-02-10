package com.github.gaboso.entity;

public class Worker {

    private final String name;
    private final String socialSecurityId;

    public Worker(String name, String socialSecurityId) {
        this.name = name;
        this.socialSecurityId = socialSecurityId;
    }

    public String getName() {
        return name;
    }

    public String getSocialSecurityId() {
        return socialSecurityId;
    }

}