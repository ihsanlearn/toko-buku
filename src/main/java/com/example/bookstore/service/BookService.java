package com.example.bookstore.service;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

public class BookService {

    private final BookRepository repo;
    private List<Book> cache;

    public BookService() {
        this.repo = new BookRepository();
        this.cache = repo.getAll();   // always non-null from repo
    }

    // Return a safe copy
    public List<Book> getAllBooks() {
        return new ArrayList<>(cache);
    }

    // Add
    public void addBook(Book book) {
        validateBook(book);

        book.setId(generateNextId());

        cache.add(book);
        repo.saveAll(cache);
    }

    // Update
    public void updateBook(Book updatedBook) {
        validateBook(updatedBook);

        Book existing = getById(updatedBook.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Book not found with id: " + updatedBook.getId());
        }

        // Update fields
        existing.setTitle(updatedBook.getTitle());
        existing.setAuthor(updatedBook.getAuthor());
        existing.setPrice(updatedBook.getPrice());
        existing.setStock(updatedBook.getStock());
        existing.setImgPath(updatedBook.getImgPath());

        repo.saveAll(cache);
    }

    // Delete
    public void deleteBook(int id) {
        boolean removed = cache.removeIf(b -> b.getId() == id);

        if (!removed) {
            throw new IllegalArgumentException("Book not found with id: " + id);
        }

        repo.saveAll(cache);
    }

    // Get by ID
    public Book getById(int id) {
        return cache.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Generate next ID (business logic → belongs here)
    private int generateNextId() {
        return cache.stream()
                .mapToInt(Book::getId)
                .max()
                .orElse(0) + 1;
    }

    // Validation
    private void validateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        if (book.getAuthor() == null || book.getAuthor().isBlank()) {
            throw new IllegalArgumentException("Author cannot be empty");
        }

        if (book.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        if (book.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
    }
}