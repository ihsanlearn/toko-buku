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


package com.example.bookstore.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;

public class JsonUtil {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static <T> T load(String path, Class<T> clazz) {
        try (FileReader reader = new FileReader(path)) {
            return gson.fromJson(reader, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void save(Object data, String path) {
        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(data, writer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

