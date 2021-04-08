package com.example.gof.behavior.visitor;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-10 10:16
 **/
public class Extractor implements Visitor{
    @Override
    public void visit(PdfFile pdfFile) {
        System.out.println("extract pdf file.");
    }

    @Override
    public void visit(PptFile pptFile) {
        System.out.println("extract ppt file.");
    }

    @Override
    public void visit(WordFile wordFile) {
        System.out.println("extract word file.");
    }
}
