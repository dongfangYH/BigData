package com.example.design_pattern.behavior.visitor;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-10 10:13
 **/
public class Compressor implements Visitor{
    @Override
    public void visit(PdfFile pdfFile) {
        System.out.println("compress pdf file.");
    }

    @Override
    public void visit(PptFile pptFile) {
        System.out.println("compress ppt file.");
    }

    @Override
    public void visit(WordFile wordFile) {
        System.out.println("compress word file.");
    }
}
