package com.example.bookstore.repository;

import java.util.List;

import com.example.bookstore.model.Book;
import com.example.bookstore.storage.BookData;
import com.example.bookstore.utils.JsonUtil;

public class BookRepository {
  private static final String PATH = "data/books.json";

  public List<Book> getAll() {
    BookData data = JsonUtil.load(PATH, BookData.class);
    return data.books;
  }

  public void saveAll(List<Book> books) {
    BookData data = new BookData();
    data.books = books;
    JsonUtil.save(data, PATH);
  }
}
