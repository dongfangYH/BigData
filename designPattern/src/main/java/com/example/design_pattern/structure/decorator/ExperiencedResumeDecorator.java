package com.example.design_pattern.structure.decorator;

/**
 * 丰富经验简历装饰器
 **/
public class ExperiencedResumeDecorator extends ResumeDecorator{

    public ExperiencedResumeDecorator(Resume resume) {
        super(resume);
    }

    /**
     * 展示丰富的工作经验
     */
    public void showExperience(){
        System.out.println("experienced resume.");
    }

    @Override
    public void info() {
        showExperience();
        this.resume.info();
    }
}
