package com.example.bookstore.service;

import com.example.bookstore.model.Book;
import com.example.bookstore.util.JsonUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    private final File dbFile = new File("data/books.json");
    private List<Book> books;

    public BookService() {
        try {
            books = new ArrayList<>(JsonUtil.loadBooks(dbFile));
        } catch (Exception e) {
            books = new ArrayList<>();
        }
    }

    public List<Book> getBooks() { return books; }

    public void addBook(Book book) {
        books.add(book);
        try {
            JsonUtil.saveBooks(dbFile, books);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}