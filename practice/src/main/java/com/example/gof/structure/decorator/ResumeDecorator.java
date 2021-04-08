package com.example.gof.structure.decorator;

/**
 * 简历装饰器
 */
public abstract class ResumeDecorator extends Resume{
    Resume resume;
    public ResumeDecorator(Resume resume) {
        this.resume = resume;
    }
}
