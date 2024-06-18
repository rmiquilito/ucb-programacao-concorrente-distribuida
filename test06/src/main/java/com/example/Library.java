package com.example;

import java.io.ObjectOutputStream;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Library {
    private List<Book> books;
    private ReentrantLock lock = new ReentrantLock();

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void listBooks(ObjectOutputStream oos) throws Exception {
    }

    public void listMembers(ObjectOutputStream oos) throws Exception {
    }

    public Boolean rentBook(String title, String name) throws Exception {
    }

    public Boolean registerBook(String object) throws Exception {
    }

    public Boolean returnBook(String title, String name) throws Exception {
    }
}
