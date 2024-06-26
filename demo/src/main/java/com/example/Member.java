package com.example;

import java.util.List;

public class Member {
    private String name;
    private List<Book> books;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "{"
                + "\"name\": \"" + this.name + "\", "
                + "\"books\": " + this.books.toString()
                + "}";
    }
}
