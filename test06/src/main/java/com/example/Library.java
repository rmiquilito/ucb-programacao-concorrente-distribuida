package com.example;

import java.io.File;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
        for (Book book : books) {
            oos.writeObject(book.toString());
            oos.flush();
        }
    }

    public void listMembers(ObjectOutputStream oos) throws Exception {
        if (Server.members.getMembers().isEmpty()) {
            oos.writeObject("Empty.");
        }
        for (Member member : Server.members.getMembers()) {
            oos.writeObject(member.toString());
            oos.flush();
        }
    }

    public Boolean rentBook(String title, String name) throws Exception {
        Member member = Server.members.fetchMember(name);
        if (member == null) {
            member = Server.members.registerMember(name);
            if (member == null) {
                return false;
            } else {
                Server.members.getMembers().add(member);
            }
        }

        Book book = fetchBook(title);
        if (book != null ? book.getAmount() > 0 : false) {
            book.setAmount(book.getAmount() - 1);

            ObjectMapper objectMapper = new ObjectMapper();
            String object = objectMapper.writeValueAsString(book);
            Book newCopy = objectMapper.readValue(object, Book.class);
            newCopy.setAmount(1);
            Book copy = Server.members.fetchBook(title, name);

            if (copy == null) {
                member.getBooks().add(newCopy);
            } else {
                copy.setAmount(copy.getAmount() + 1);
            }

            refreshLibrary();
            Server.members.refreshMembers();
            return true;
        }
        return false;
    }

    public Boolean registerBook(String object) throws Exception {
        Book newBook;
        try {
            newBook = new ObjectMapper().readValue(object, Book.class);
        } catch (Exception e) {
            return false;
        }

        Book book = fetchBook(newBook.getTitle());
        if (book == null) {
            books.add(newBook);
        } else {
            book.setAmount(book.getAmount() + newBook.getAmount());
        }
        refreshLibrary();
        return true;
    }

    public Boolean returnBook(String title, String name) throws Exception {
        Member member = Server.members.fetchMember(name);
        if (member == null) {
            member = Server.members.registerMember(name);
            if (member != null) {
                Server.members.getMembers().add(member);
            }
            return false;
        }

        Book copy = Server.members.fetchBook(title, name);
        if (copy == null) {
            return false;
        } else {
            Book book = fetchBook(title);
            copy.setAmount(copy.getAmount() - 1);
            if (copy.getAmount() == 0) {
                member.getBooks().remove(copy);
            }
            book.setAmount(book.getAmount() + 1);

            refreshLibrary();
            Server.members.refreshMembers();
        }
        return true;
    }
}
