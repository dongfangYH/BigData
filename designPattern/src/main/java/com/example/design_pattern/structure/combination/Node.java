package com.example.design_pattern.structure.combination;

public abstract class Node {

    private final String name;
    protected Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
