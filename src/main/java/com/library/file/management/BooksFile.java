package com.library.file.management;

import com.library.book.Book;
import com.library.book.genre.Genre;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BooksFile {
    private static final String filePath = "C:\\Java\\intelliJ\\projects\\LibraryTTPSC\\src\\main\\resources\\data\\books.csv";

    public static void writeBook(Book book) {
        List<Book> books = getBooks();
        if (books.stream().anyMatch(b -> b.getId().equals(book.getId()))) {
            books.stream().filter(b -> b.getId().equals(book.getId()))
                    .forEach(b -> {
                        b.setTitle(book.getTitle());
                        b.setGenre(book.getGenre());
                        b.setReleaseDate(book.getReleaseDate());
                        b.setAvailable(book.isAvailable());
                        b.setIdStudent(book.getIdStudent());
                    });
        } else books.add(book);
        File file = new java.io.File(filePath);
        try {
            PrintWriter writer = new PrintWriter(file);
            for (Book b : books) writer.println(b);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        try(BufferedReader reader = Files.newBufferedReader(Path.of(filePath), StandardCharsets.UTF_8)) {
            String line = reader.readLine();
            while(line != null) {
                String[] attributes = line.split(",");
                for(int i = 0; i < attributes.length; i++) {
                    attributes[i] = attributes[i].substring(attributes[i].indexOf("=")+1);
                }
                attributes[attributes.length-1] = attributes[attributes.length-1].replace(")", "");
                Book book = createBook(attributes);
                books.add(book);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    private static Book createBook(String[] attributes) {
        Book book = new Book();
        UUID id = UUID.fromString(attributes[0]);
        String title = attributes[1];
        Genre genre = Genre.valueOf(attributes[2]);
        LocalDate releaseDate = LocalDate.parse(attributes[3]);
        boolean available = Boolean.parseBoolean(attributes[4]);
        if(!attributes[5].equals("null")) {
            UUID idStudent = UUID.fromString(attributes[5]);
            book.setIdStudent(idStudent);
        }
        book.setId(id);
        book.setTitle(title);
        book.setGenre(genre);
        book.setReleaseDate(releaseDate);
        book.setAvailable(available);
        return book;
    }
}
