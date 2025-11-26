package com.example.bookstore.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Transaction;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.TransactionRepository;

public class TransactionService {

    private final TransactionRepository transactionRepository = new TransactionRepository();
    private final BookRepository bookRepository = new BookRepository();

    public void buyBook(int userId, int bookId, int quantity, String address, String courier) {
        List<Book> books = bookRepository.getAll();
        Book book = books.stream()
                .filter(b -> b.getId() == bookId)
                .findFirst()
                .orElse(null);

        if (book == null) {
            throw new RuntimeException("Book not found");
        }

        double totalPrice = book.getPrice() * quantity;

        List<Transaction> transactions = transactionRepository.getAll();
        if (transactions == null) transactions = new ArrayList<>();

        int newId = transactions.size() + 1;

        Transaction transaction = new Transaction(
                newId,
                userId,
                bookId,
                quantity,
                totalPrice,
                LocalDateTime.now(),
                "Dikemas",
                address,
                courier
        );

        transactions.add(transaction);
        transactionRepository.saveAll(transactions);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.getAll();
    }

    public List<Transaction> getTransactionsByUserId(int userId) {
        return transactionRepository.getAll()
                .stream()
                .filter(t -> t.getUserId() == userId)
                .toList();
    }
}
