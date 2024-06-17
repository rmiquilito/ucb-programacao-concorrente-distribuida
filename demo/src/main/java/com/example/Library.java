package com.example;

import java.io.File;
import java.io.ObjectOutputStream;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Library {
    private List<Book> books;

    public synchronized List<Book> getBooks() {
        return books;
    }

    public synchronized void setBooks(List<Book> books) {
        this.books = books;
    }

    public static void refreshLibrary() throws Exception {
        new ObjectMapper().writeValue(new File("src/main/resources/books.json"), Server.library);
    }

    public void listBooks(ObjectOutputStream oos) throws Exception {
        for (Book book : books) {
            oos.writeObject(book.toString());
        }
    }

    public Boolean rentBook(String title) throws Exception {
        for (Book book : Server.library.getBooks()) {
            if (title.equals(book.getTitle()) && book.getAmount() > 0) {
                book.setAmount(book.getAmount() - 1);
                refreshLibrary();
                return true;
            }
        }
        return false;
    }

    public Boolean addBook(String object) throws Exception {
        Book newBook;
        try {
            newBook = new ObjectMapper().readValue(object, Book.class);
        } catch (Exception e) {
            return false;
        }
        for (Book book : books) {
            if (newBook.getTitle().equals(book.getTitle())) {
                book.setAmount(book.getAmount() + newBook.getAmount());
                refreshLibrary();
                return true;
            }
        }
        books.add(newBook);
        refreshLibrary();
        return true;
    }

    public Boolean returnBook(String title) throws Exception {
        for (Book book : books) {
            if (title.equals(book.getTitle())) {
                book.setAmount(book.getAmount() + 1);
                refreshLibrary();
                return true;
            }
        }
        return false;
    }
}
