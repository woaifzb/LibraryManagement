package com.library.manage.services;

import com.library.manage.Domain.Book;
import com.library.manage.Domain.DTO.BookDTO;
import com.library.manage.Domain.DTO.EntityDTOConverter;
import com.library.manage.ErrorHandlers.AlreadyExistsException;
import com.library.manage.ErrorHandlers.NotFoundException;
import com.library.manage.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 书籍相关接口
 */
@Service
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository ) {
        this.bookRepository = bookRepository;
    }

    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(EntityDTOConverter::convertBookToDTO)
                .collect(Collectors.toList());
    }
    public BookDTO getBookById(int id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found with id " + id));
        return EntityDTOConverter.convertBookToDTO(book);
    }

    public List<BookDTO> searchByTitle(String name) {
        List<Book> books = bookRepository.searchByTitle(name);
        return books.stream()
                .map(EntityDTOConverter::convertBookToDTO)
                .collect(Collectors.toList());
    }



    public Book createBook(Book newBook) {
        if (bookRepository.existsByTitle(newBook.getTitle())) {
            throw new AlreadyExistsException("Book with title '" + newBook.getTitle() + "' already exists.");
        }
        return bookRepository.save(newBook);
    }

    public void deleteBook(int id){
        if (!bookRepository.existsById(id)) {
            throw new NotFoundException("Book with id '" + id + "' does not exists.");
        }
        bookRepository.deleteById(id);
    }

    public Book updateBook(int id, Book updatedBook) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book task = optionalBook.get();
            task.setTitle(updatedBook.getTitle());
            return bookRepository.save(task);
        } else {
            throw new NotFoundException("Book with id " + id + " not found.");
        }
    }
    public Book patchBook(int id, Book updatedBook) {
        Optional<Book> optionalTask = bookRepository.findById(id);
        if (optionalTask.isPresent()) {
            Book task = optionalTask.get();
            if (updatedBook.getTitle() != null) {
                task.setTitle(updatedBook.getTitle());
            }
            if (updatedBook.getBorrowed() != null) {
                if(updatedBook.getBorrowed()){
                    task.setborrowDate(LocalDate.now());
                }
                task.setBorrowed(updatedBook.getBorrowed());
            }
            return bookRepository.save(task);
        } else {
            throw new NotFoundException("Book with id " + id + " not found.");
        }
    }



}
