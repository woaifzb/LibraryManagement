package com.library.manage.Domain.DTO;

import java.time.LocalDate;
import java.util.Set;

public class BookDTO {
    private int id;
    private String title;
    private boolean isBorrowed;
    private LocalDate borrowDate;




    public BookDTO() {
    }

    public BookDTO(int id, String title, boolean isBorrowed,  String borrowerName , LocalDate borrowDate) {
        this.id = id;
        this.title = title;
        this.isBorrowed = isBorrowed;
        this.borrowDate = borrowDate;

    }
    public LocalDate getborrowDate() {
        return borrowDate;
    }

    public void setborrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }


}
