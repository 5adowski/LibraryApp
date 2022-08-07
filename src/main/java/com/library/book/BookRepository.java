package com.library.book;

import com.library.file.management.BooksFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BookRepository {

    public static Book getById(UUID id, List<Book> books) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst().get();
    }

    public static List<Book> getBooksSortedByTitle(List<Book> books) {
        return books.stream()
                .sorted((b1, b2) -> b1.getTitle().compareToIgnoreCase(b2.getTitle()))
                .toList();
    }

    public static List<Book> getAvaiableBooks(List<Book> books) {
        return books.stream().filter(Book::isAvailable).toList();
    }

}
