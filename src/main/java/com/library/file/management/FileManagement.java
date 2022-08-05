package com.library.file.management;

import com.library.book.Book;
import com.library.book.genre.Genre;
import com.library.student.Student;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileManagement {
    public static void writeBookToFile(Book book, String filePath) throws IOException {
        List<Book> bookList = getBooksFromFile(filePath);
        bookList.add(book);
        File file = new File(filePath);
        PrintWriter writer = new PrintWriter(file);
        for(Book b : bookList) writer.println(b);
        writer.close();
    }

    public static List<Book> getBooksFromFile(String filePath) throws IOException {
        List<Book> bookList = new ArrayList<>();
        File file = new File(filePath);
        BufferedReader reader = Files.newBufferedReader(Path.of(filePath));
        String line = reader.readLine();
        while (line != null) {
            String[] attributes = line.split(",");
            for(int i = 0; i < attributes.length; i++) {
                attributes[i] = attributes[i].substring(attributes[i].indexOf("=")+1);
            }
            attributes[attributes.length-1] = attributes[attributes.length-1].replace(")", "");
            Book book = createBook(attributes);
            bookList.add(book);
            line = reader.readLine();
        }
        return bookList;
    }

    private static Book createBook(String[] attributes){
        Book book = new Book();
        UUID id = UUID.fromString(attributes[0]);
        String title = attributes[1];
        Genre genre = Genre.valueOf(attributes[2]);
        LocalDate releaseDate = LocalDate.parse(attributes[3]);
        boolean available = Boolean.parseBoolean(attributes[4]);
        book.setId(id);
        book.setTitle(title);
        book.setGenre(genre);
        book.setReleaseDate(releaseDate);
        book.setAvailable(available);
        return book;
    }

    public static void writeStudentToFile(Student student, String filePath) throws IOException {
        List<Student> studentList = getStudentsFromFile(filePath);
        studentList.add(student);
        File file = new File(filePath);
        PrintWriter writer = new PrintWriter(file);
        for(Student s : studentList) writer.println(s);
        writer.close();
    }

    public static List<Student> getStudentsFromFile(String filePath) throws IOException {
        List<Student> studentList = new ArrayList<>();
        File file = new File(filePath);
        BufferedReader reader = Files.newBufferedReader(Path.of(filePath));
        String line = reader.readLine();
        while (line != null) {
            String[] attributes = line.split(",");
            for(int i = 0; i < attributes.length; i++) {
                attributes[i] = attributes[i].substring(attributes[i].indexOf("=")+1);
            }
            attributes[attributes.length-1] = attributes[attributes.length-1].replace(")", "");
            Student student = createStudent(attributes);
            studentList.add(student);
            line = reader.readLine();
        }
        return studentList;
    }

    private static Student createStudent(String[] attributes){
        Student student = new Student();
        UUID id = UUID.fromString(attributes[0]);
        String firstName = attributes[1];
        String lastName = attributes[2];
        LocalDate birthDate = LocalDate.parse(attributes[3]);
        student.setId(id);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setBirthDate(birthDate);
        return student;
    }
}
