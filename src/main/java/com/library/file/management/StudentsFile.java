package com.library.file.management;

import com.library.student.Student;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StudentsFile {
    private static final String filePath = "C:\\Java\\intelliJ\\projects\\LibraryTTPSC\\src\\main\\resources\\data\\students.csv";

    public static void writeStudent(Student student) {
        List<Student> students = getStudents();
        if (students.stream().anyMatch(s -> s.getId().equals(student.getId()))) {
            students.stream().filter(s -> s.getId().equals(student.getId()))
                    .forEach(s -> {
                        s.setId(student.getId());
                        s.setFirstName(student.getFirstName());
                        s.setLastName(student.getLastName());
                        s.setBirthDate(student.getBirthDate());
                        s.setIdsBooksRented(student.getIdsBooksRented());
                    });
        } else students.add(student);
        File file = new File(filePath);
        try {
            PrintWriter writer = new PrintWriter(file);
            for (Student s : students) writer.println(s);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath), StandardCharsets.UTF_8)) {
            String line = reader.readLine();
            while (line != null) {
                String[] attributes = line.split(",");
                for (int i = 0; i < attributes.length; i++) {
                    attributes[i] = attributes[i].substring(attributes[i].indexOf("=") + 1);
                    attributes[i] = attributes[i].replace("[", "");
                    attributes[i] = attributes[i].replace("]", "");
                    attributes[i] = attributes[i].replace(" ", "");
                    attributes[i] = attributes[i].replace(")", "");
                }
                Student student = createStudent(attributes);
                students.add(student);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }

    private static Student createStudent(String[] attributes) {
        Student student = new Student();
        UUID id = UUID.fromString(attributes[0]);
        String firstName = attributes[1];
        String lastName = attributes[2];
        LocalDate birthDate = LocalDate.parse(attributes[3]);
        if (!attributes[4].equals("null") && !attributes[4].equals("")) {
            List<UUID> idsBooksRented = new ArrayList<>();
            for (int i = 4; i < attributes.length; i++) idsBooksRented.add(UUID.fromString(attributes[i]));
            student.setIdsBooksRented(idsBooksRented);
        }
        student.setId(id);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setBirthDate(birthDate);
        return student;
    }
}
