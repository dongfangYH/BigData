package com.example.gof.behavior.visitor;

public interface Visitor {

    void visit(PdfFile pdfFile);

    void visit(PptFile pptFile);

    void visit(WordFile wordFile);
}
