package com.example.design_pattern.structure.decorator;

/**
 * 简历外观装饰器
 **/
public class GoodPreviewResumeDecorator extends ResumeDecorator{
    public GoodPreviewResumeDecorator(Resume resume) {
        super(resume);
    }

    /**
     * 展示精美的简历外观
     */
    public void showGoodPreview(){
        System.out.println("this resume looks good.");
    }

    @Override
    public void info() {
        showGoodPreview();
        this.resume.info();
    }
}
