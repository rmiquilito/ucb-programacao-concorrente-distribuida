package com.example;

public class Book {
    private String title;
    private String author;
    private String genre;
    private Integer amount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "{"
                + "\"title\": \"" + this.title + "\", "
                + "\"author\": \"" + this.author + "\", "
                + "\"genre\": \"" + this.genre + "\", "
                + "\"amount\": " + this.amount
                + "}";
    }
}
