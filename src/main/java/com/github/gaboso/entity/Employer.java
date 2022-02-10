package com.github.gaboso.entity;

public class Employer {

    private final String name;
    private final String socialSecurityId;

    public Employer(String name, String socialSecurityId) {
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
