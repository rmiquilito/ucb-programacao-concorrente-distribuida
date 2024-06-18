package com.example;

import java.io.File;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Library {
    private List<Book> books;
    private ReentrantLock lock = new ReentrantLock();

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void refreshLibrary() throws Exception {
        new ObjectMapper().writeValue(new File("src/main/resources/books.json"), Server.library);
    }

    public Book fetchBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
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
