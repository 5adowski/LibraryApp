package com.library.student;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class Student {
    private UUID id = UUID.randomUUID();
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private List<UUID> idsOfBooksRented;


}
