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
        this.cache = repo.getAll();
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(cache);
    }

    public void addBook(Book book) {
        validateBook(book);

        book.setId(generateNextId());

        cache.add(book);
        repo.saveAll(cache);
    }

    public void updateBook(Book updatedBook) {
        validateBook(updatedBook);

        Book existing = getById(updatedBook.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Book not found with id: " + updatedBook.getId());
        }

        existing.setTitle(updatedBook.getTitle());
        existing.setAuthor(updatedBook.getAuthor());
        existing.setPrice(updatedBook.getPrice());
        existing.setStock(updatedBook.getStock());
        existing.setImgPath("com/example/bookstore/images/" + extractFileName(updatedBook.getImgPath()));

        repo.saveAll(cache);
    }

    public void deleteBook(int id) {
        boolean removed = cache.removeIf(b -> b.getId() == id);

        if (!removed) {
            throw new IllegalArgumentException("Book not found with id: " + id);
        }

        repo.saveAll(cache);
    }

    public Book getById(int id) {
        return cache.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    private int generateNextId() {
        return cache.stream()
                .mapToInt(Book::getId)
                .max()
                .orElse(0) + 1;
    }

    private void validateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().isBlank()) {
            throw new IllegalArgumentException("judul gaboleh kosong");
        }

        if (book.getAuthor() == null || book.getAuthor().isBlank()) {
            throw new IllegalArgumentException("penulis gaboleh kosong");
        }

        if (book.getPrice() < 0) {
            throw new IllegalArgumentException("harga gabisa negatif");
        }

        if (book.getStock() < 0) {
            throw new IllegalArgumentException("stok gabisa negatif");
        }
    }

    private static String extractFileName(String path) {
        if (path == null || path.isBlank()) return "";
        int idx = path.lastIndexOf('/');
        if (idx == -1) return path;
        return path.substring(idx + 1);
    }
}