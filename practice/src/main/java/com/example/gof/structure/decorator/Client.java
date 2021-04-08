package com.example.gof.structure.decorator;

public class Client {

    public static void main(String[] args){
        Resume resume = new NormalResume();
        resume = new ExperiencedResumeDecorator(resume);
        resume = new GoodPreviewResumeDecorator(resume);
        resume.info();
    }
}
