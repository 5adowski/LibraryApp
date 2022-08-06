package com.library.book;

import com.library.file.management.FileManagement;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BookRepository {

    public static Book getById(String id) throws IOException {
        UUID uuid = UUID.fromString(id);
        List<Book> bookList = FileManagement.getBooksFromFile("C:\\Java\\intelliJ\\projects\\LibraryTTPSC\\src\\main\\resources\\data\\books.csv");
        Optional<Book> matchingBook = bookList.stream()
                .filter(book -> book.getId().equals(uuid))
                .findFirst();
        return matchingBook.get();
    }

}
