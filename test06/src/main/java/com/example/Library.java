package com.example;

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
}
