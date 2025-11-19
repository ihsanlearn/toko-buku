package com.example.bookstore.controller;

import java.util.List;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;

public class ManageBooksController {
  private List<Book> books = new BookRepository().getAll();

  public void initialize() {
    for (Book b : books) {
      System.out.println(b.getTitle());

      // dengan contoh iki koe entuk bahwa b ki wes buku real seko json
      // dan books kui = kumpulan buku real seko json
    }
  }

}
