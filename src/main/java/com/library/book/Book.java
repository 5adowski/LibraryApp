package com.library.book;


import com.library.book.genre.Genre;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class Book {
    private UUID id = UUID.randomUUID();
    private String title;
    private Enum<Genre> genre;
    private LocalDate releaseDate;
    private boolean isAvailable;
    private UUID idOfStudent;

    public Book(){
        this.isAvailable = idOfStudent == null;
    }

    public void setIdOfStudent(UUID idOfStudent) {
        this.idOfStudent = idOfStudent;
        if(idOfStudent != null) this.isAvailable = false;
    }
}
