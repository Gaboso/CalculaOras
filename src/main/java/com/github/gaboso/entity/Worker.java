package com.github.gaboso.entity;

public class Worker {

    private String name;
    private String pis;

    public Worker(String name, String pis) {
        this.name = name;
        this.pis = pis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPis() {
        return pis;
    }

    public void setPis(String pis) {
        this.pis = pis;
    }

}