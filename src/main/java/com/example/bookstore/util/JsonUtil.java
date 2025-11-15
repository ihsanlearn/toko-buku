// package com.example.bookstore.util;

// import com.fasterxml.jackson.core.type.TypeReference;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.example.bookstore.model.Book;

// import java.io.File;
// import java.util.List;

// public class JsonUtil {
//     private static final ObjectMapper mapper = new ObjectMapper();

//     public static List<Book> loadBooks(File file) throws Exception {
//         if (!file.exists()) return List.of();
//         return mapper.readValue(file, new TypeReference<>() {});
//     }

//     public static void saveBooks(File file, List<Book> books) throws Exception {
//         mapper.writerWithDefaultPrettyPrinter().writeValue(file, books);
//     }
// }
