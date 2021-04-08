package com.example.gof.creator.builder;


public class Main {

    public static void main(String[] args){
        Director director = new Director();
        director.buildK30Pro().call();
        director.buildK40Pro().call();
        director.buildMi10Pro().call();
        director.buildMix4().call();
    }
}
