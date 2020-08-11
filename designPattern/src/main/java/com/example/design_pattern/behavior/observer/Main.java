package com.example.design_pattern.behavior.observer;

public class Main {

    public static void main(String[] args){
        Hero hero = new Hero("new");
        Follower f1 = new Follower("f1");
        Follower f2 = new Follower("f2");

        hero.addObserver(f1);
        hero.addObserver(f2);

        hero.notifyObserver("beat bad man.");

        hero.removeObserver(f1);

        hero.notifyObserver("go to school");

    }
}
