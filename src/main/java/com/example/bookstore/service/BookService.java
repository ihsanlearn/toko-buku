package com.example.bookstore.service;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

public class BookService {

    private final BookRepository repo;
    private List<Book> cache;  // local cache

    public BookService() {
        this.repo = new BookRepository();
        this.cache = repo.getAll();

        if (this.cache == null) {
            this.cache = new ArrayList<>();
        }
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(cache); // return copy for safety
    }

    public void addBook(Book book) {
        validateBook(book);

        book.setId(repo.getNextId());

        cache.add(book);
        repo.saveAll(cache);
    }

    public void updateBook(Book updatedBook) {
        validateBook(updatedBook);

        for (int i = 0; i < cache.size(); i++) {
            if (cache.get(i).getId() == updatedBook.getId()) {
                cache.set(i, updatedBook);
                repo.saveAll(cache);
                return;
            }
        }

        throw new IllegalArgumentException("Book not found with id: " + updatedBook.getId());
    }

    public void deleteBook(int id) {
        boolean removed = cache.removeIf(b -> b.getId() == id);

        if (!removed) {
            throw new IllegalArgumentException("Book not found: " + id);
        }

        repo.saveAll(cache);
    }

    public Book getById(int id) {
        return cache.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private void validateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().isBlank())
            throw new IllegalArgumentException("Title cannot be empty");
        if (book.getAuthor() == null || book.getAuthor().isBlank())
            throw new IllegalArgumentException("Author cannot be empty");
        if (book.getPrice() < 0)
            throw new IllegalArgumentException("Price cannot be negative");
        if (book.getStock() < 0)
            throw new IllegalArgumentException("Stock cannot be negative");
    }
}

