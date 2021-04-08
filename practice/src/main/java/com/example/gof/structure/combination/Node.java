package com.example.gof.structure.combination;

public abstract class Node {

    private final String name;
    protected Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
