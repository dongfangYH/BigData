package com.example.gof.structure.combination;

public class Main {
    public static void main(String[] args) {
        Holder root = new Holder("A");

        root.addSubNode(new SolidNode("a1"));
        root.addSubNode(new SolidNode("a2"));

        Holder holderB = new Holder("B");
        holderB.addSubNode(new SolidNode("b1"));
        root.addSubNode(holderB);

        root.list();
    }
}
