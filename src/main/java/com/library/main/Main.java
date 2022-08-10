package com.library.main;

import com.library.book.Book;
import com.library.book.BookRepository;
import com.library.book.genre.Genre;
import com.library.file.management.BooksFile;
import com.library.file.management.StudentsFile;
import com.library.student.Student;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        while (true) {
            List<Book> books = BooksFile.getBooks();
            List<Student> students = StudentsFile.getStudents();
            Scanner scan = new Scanner(System.in);
            System.out.println("Pick option:");
            System.out.println("1. Add new student");
            System.out.println("2. Add new book");
            System.out.println("3. List available books");
            System.out.println("4. List books by title");
            System.out.println("5. Rent a book");
            System.out.println("6. Return a book");
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
                StudentsFile.writeStudent(student);
                System.out.println("Student added!");
            }
            if (option == 2) {
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
                BooksFile.writeBook(book);
                System.out.println("Book added");
            }
            if (option == 3) {
                if(!(BookRepository.getAvaiableBooks(books).isEmpty() || BookRepository.getAvaiableBooks(books)==null)) {
                    for(Book b : BookRepository.getAvaiableBooks(books)) System.out.println(b);
                } else {
                    System.out.println("No available books");
                }
            }
            if (option == 4) {
                for(Book b : BookRepository.getBooksSortedByTitle(books)) System.out.println(b);
            }
            if (option == 5) {
                if(!(BookRepository.getAvaiableBooks(books).isEmpty() || BookRepository.getAvaiableBooks(books)==null)) {
                    for(Book b : BookRepository.getAvaiableBooks(books)) System.out.println(b);
                    System.out.println("Type ID of book u want to rent:");
                    UUID idBook = UUID.fromString(scan.next());

                    for(Student s : students) System.out.println(s);
                    System.out.println("Type ID of the student who is renting a book:");
                    UUID idStudent = UUID.fromString(scan.next());

                    books.stream().filter(b -> b.getId().equals(idBook))
                            .forEach(b -> {
                                b.setAvailable(false);
                                b.setIdStudent(idStudent);
                            });
                    Book book = books.stream().filter(b -> b.getId().equals(idBook)).findAny().get();
                    BooksFile.writeBook(book);

                    Student student = students.stream().filter(s -> s.getId().equals(idStudent)).findAny().get();
                    List<UUID> idsBooksRented;
                    if (student.getIdsBooksRented() != null) {
                        idsBooksRented = student.getIdsBooksRented();
                        idsBooksRented.add(idBook);
                    } else {
                        idsBooksRented = new ArrayList<>();
                        idsBooksRented.add(idBook);
                    }
                    students.stream().filter(s -> s.getId().equals(idStudent))
                            .forEach(s -> {s.setIdsBooksRented(idsBooksRented);
                            StudentsFile.writeStudent(s);});
                } else {
                    System.out.println("No books to rent");
                }
            }
            if(option == 6) {
                List<UUID> idsBooks = new ArrayList<>();
                for(Book b : books) if(!b.isAvailable()) idsBooks.add(b.getId());
                if(!(idsBooks.isEmpty())) {
                    for(Book b : books.stream().filter(b -> !b.isAvailable()).toList()) System.out.println(b);
                    System.out.println("Type ID of book u want to return:");
                    UUID idBook = UUID.fromString(scan.next());
                    Book book = books.stream().filter(b -> b.getId().equals(idBook)).findAny().get();
                    Student student = students.stream().filter(s -> s.getIdsBooksRented().contains(book.getId())).findAny().get();
                    List<UUID> idsBooksRented = student.getIdsBooksRented();
                    idsBooksRented.remove(book.getId());
                    student.setIdsBooksRented(idsBooksRented);
                    StudentsFile.writeStudent(student);
                    book.setAvailable(true);
                    book.setIdStudent(null);
                    BooksFile.writeBook(book);
                } else {
                    System.out.println("No books to return");
                }
            }
            if (option == 0) {
                break;
            }
        }
    }
}
