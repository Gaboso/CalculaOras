package com.github.gaboso.entity;

public class Employer {

    private final String name;
    private final String cnpj;

    public Employer(String name, String cnpj) {
        this.name = name;
        this.cnpj = cnpj;
    }

    public String getName() {
        return name;
    }

    public String getCnpj() {
        return cnpj;
    }

}
