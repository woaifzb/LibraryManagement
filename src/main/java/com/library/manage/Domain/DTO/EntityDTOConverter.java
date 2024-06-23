package com.library.manage.Domain.DTO;
import com.library.manage.Domain.Book;

public class EntityDTOConverter {

    public static BookDTO convertBookToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setBorrowed(book.getBorrowed());
        bookDTO.setborrowDate(book.getborrowDate());

        return bookDTO;
    }

}
