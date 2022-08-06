package com.library.main;

import com.library.book.Book;
import com.library.book.BookRepository;
import com.library.book.genre.Genre;
import com.library.file.management.FileManagement;
import com.library.student.Student;
import com.library.student.StudentRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        while (true) {
            String studentFilePath = "C:\\Java\\intelliJ\\projects\\LibraryTTPSC\\src\\main\\resources\\data\\students.csv";
            String bookFilePath = "C:\\Java\\intelliJ\\projects\\LibraryTTPSC\\src\\main\\resources\\data\\books.csv";
            List<Book> bookList = FileManagement.getBooksFromFile(bookFilePath);
            List<Student> studentList = FileManagement.getStudentsFromFile(studentFilePath);
            Scanner scan = new Scanner(System.in);
            System.out.println("Pick option:");
            System.out.println("1. Add new student");
            System.out.println("2. List available books");
            System.out.println("3. List books by title");
            System.out.println("4. Rent a book");
            System.out.println("5. Return a book");
            System.out.println("6. Save files");
            System.out.println("0. Close program");
            int option = scan.nextInt();
            if (option == 1) {
                Student student = new Student();
                System.out.println("Enter first name:");
                student.setFirstName(scan.next());
                System.out.println("Enter last name:");
                student.setLastName(scan.next());
                System.out.println("Enter day of birth:");
                int day = scan.nextInt();
                System.out.println("Enter month of birth:");
                int month = scan.nextInt();
                System.out.println("Enter year of birth:");
                int year = scan.nextInt();
                student.setBirthDate(LocalDate.of(year, month, day));
                FileManagement.writeStudentToFile(student, studentFilePath);
                System.out.println("Student added!");
            }
            if (option == 2) {
                bookList.stream().filter(Book::isAvailable).forEach(System.out::println);
            }
            if (option == 3) {
                List<String> titleList = new ArrayList<>();
                for (Book book : bookList) titleList.add(book.getTitle());
                titleList.sort(String::compareToIgnoreCase);
                System.out.println(titleList);
            }
            if (option == 4) {

                System.out.println(bookList);
                System.out.println("Type ID of book u want to rent:");
                String idOfBookPicked = scan.next();
                Book book = BookRepository.getById(idOfBookPicked);

                System.out.println(studentList);
                System.out.println("Type ID of the student who is renting a book:");
                String idOfStudentPicked = scan.next();
                Student student = StudentRepository.getById(idOfStudentPicked);

                book.setIdOfStudent(UUID.fromString(idOfStudentPicked));
                bookList.stream()
                        .filter(b -> UUID.fromString(idOfBookPicked).equals(book.getId()))
                        .forEach(b -> b.setIdOfStudent(UUID.fromString(idOfStudentPicked)));
                FileManagement.writeBookToFile(book, bookFilePath);

                List<UUID> idsOfBooksRented = student.getIdsOfBooksRented();
                if (idsOfBooksRented == null) idsOfBooksRented = new ArrayList<>();
                idsOfBooksRented.add(UUID.fromString(idOfBookPicked));
                student.setIdsOfBooksRented(idsOfBooksRented);
                List<UUID> finalIdsOfBooksRented = idsOfBooksRented;
                studentList.stream()
                        .filter(s -> s.getId().equals(student.getId()))
                                .forEach(s->s.setIdsOfBooksRented(finalIdsOfBooksRented));
                FileManagement.writeStudentToFile(student, studentFilePath);
            }
            if (option == 0) {
                break;
            }
            if (option == 7) {
                System.out.println("Adding new book");
                Book book = new Book();
                System.out.println("Enter book title:");
                scan.nextLine();
                book.setTitle(scan.nextLine());
                System.out.println("Enter genre of book:");
                System.out.println("ACTION");
                System.out.println("ROMANCE");
                System.out.println("FANTASY");
                book.setGenre(Genre.valueOf(scan.next()));
                System.out.println("Enter day of release:");
                int day = scan.nextInt();
                System.out.println("Enter month of release:");
                int month = scan.nextInt();
                System.out.println("Enter year of release:");
                int year = scan.nextInt();
                book.setReleaseDate(LocalDate.of(year, month, day));
                bookList = FileManagement.getBooksFromFile(bookFilePath);
                bookList.add(book);
                FileManagement.writeBookToFile(book, bookFilePath);
                System.out.println("Book added");
            }
            if(option==8) {
                FileManagement.writeBookToFile(new Book(), bookFilePath);
            }
        }
    }
}