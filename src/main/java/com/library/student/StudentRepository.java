package com.library.student;

import com.library.book.Book;
import com.library.file.management.FileManagement;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class StudentRepository {

    public static Student getById(String id) throws IOException {
        UUID uuid = UUID.fromString(id);
        List<Student> studentList = FileManagement.getStudentsFromFile("C:\\Java\\intelliJ\\projects\\LibraryTTPSC\\src\\main\\resources\\data\\students.csv");
        Optional<Student> matchingStudent = studentList.stream()
                .filter(student -> student.getId().equals(uuid))
                .findAny();
        return matchingStudent.get();
    }

}
