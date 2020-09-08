package com.example.design_principle.ISP;

public interface IBookService {
    void query(String sid);
    void addBook(Book book);
    void removeBook(String sid);
}
