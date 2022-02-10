package com.github.gaboso.entity;

public class Worker {

    private final String name;
    private final String pis;

    public Worker(String name, String pis) {
        this.name = name;
        this.pis = pis;
    }

    public String getName() {
        return name;
    }

    public String getPis() {
        return pis;
    }

}