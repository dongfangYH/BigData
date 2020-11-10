package com.example.design_pattern.behavior.visitor;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-10 10:16
 **/
public class Main {
    public static void main(String[] args){
        ResourceFile pdfFile = new PdfFile("c://a.pdf");
        ResourceFile pptFile = new PdfFile("c://a.ppt");
        ResourceFile wordFile = new PdfFile("c://a.word");

        Extractor extractor = new Extractor();
        Compressor compressor = new Compressor();

        pdfFile.accept(extractor);
        pdfFile.accept(compressor);

        pptFile.accept(extractor);

        wordFile.accept(compressor);
    }
}
