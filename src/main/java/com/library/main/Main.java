package com.library.main;

import com.library.book.Book;
import com.library.file.management.FileManagement;
import com.library.student.Student;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        while(true) {
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
            if(option==1) {
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
            if(option==2) {
                bookList.stream().filter(Book::isAvailable).forEach(System.out::println);
            }
            if(option==3) {
                List<String> titleList = new ArrayList<>();
                for(Book book : bookList) titleList.add(book.getTitle());
                titleList.sort(String::compareToIgnoreCase);
                System.out.println(titleList);
            }
            if(option==4) {
                
            }
        }
    }
}