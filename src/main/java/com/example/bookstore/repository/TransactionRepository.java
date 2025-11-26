package com.example.bookstore.repository;

import java.util.List;

import com.example.bookstore.model.Transaction;
import com.example.bookstore.storage.TransactionData;
import com.example.bookstore.utils.JsonUtil;

public class TransactionRepository {

    private static final String PATH = "data/transactions.json";

    public List<Transaction> getAll() {
        TransactionData data = JsonUtil.load(PATH, TransactionData.class);
        return data.transactions;
    }

    public void saveAll(List<Transaction> transactions) {
        TransactionData data = new TransactionData();
        data.transactions = transactions;
        JsonUtil.save(data, PATH);
    }
}

