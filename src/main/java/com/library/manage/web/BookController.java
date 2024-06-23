package com.library.manage.web;

import com.library.manage.Domain.Book;
import com.library.manage.Domain.DTO.BookDTO;
import com.library.manage.ErrorHandlers.ErrorResponse;
import com.library.manage.ErrorHandlers.AlreadyExistsException;
import com.library.manage.ErrorHandlers.NotFoundException;

import com.library.manage.services.BookService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Tag(name = "BookController", description = "图书管理")
@RestController
@RequestMapping("/BooksAPI")
public class BookController {
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "获取所有图书")
    @GetMapping
    public List<BookDTO> getAllbooks() {
        return  bookService.getAllBooks();
    }
    @Operation(summary = "根据id查找图书")
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable int id) {
        try {
            BookDTO book = bookService.getBookById(id);
            return ResponseEntity.ok(book);
        } catch (NotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    @Operation(summary = "根据名称查找图书（模糊查询）")
    @GetMapping("/title/{name}")
        public ResponseEntity<?> getBookByTitle(@PathVariable String name) {
        try {
            List<BookDTO> books = bookService.searchByTitle(name);
            return ResponseEntity.ok(books);
        } catch (NotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @Operation(summary = "创建图书")
    @PostMapping
    public ResponseEntity<?> createBook(@Valid @RequestBody Book newBook , BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return ResponseEntity.badRequest().body(errors);
        }try {
            Book book = bookService.createBook(newBook);
            return ResponseEntity.status(HttpStatus.CREATED).body(book);
        } catch (AlreadyExistsException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * DELETE /BooksAPI/{id}
     */
    @Operation(summary = "根据id删除")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable int id){
        try {
            bookService.deleteBook(id);
            return ResponseEntity.status(HttpStatus.OK).body("Book with id " + id + " deleted successfully");
        }
        catch (NotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * PUT /BooksAPI/{id} - 根据id更新 (替换)
     */
    @Hidden
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePutBook(@PathVariable int id, @RequestBody Book updatedBook) {
        try {
            Book book = bookService.updateBook(id, updatedBook);
            return ResponseEntity.ok(book);
        } catch (NotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    /**
     * PATCH /BooksAPI/{id} - 根据id更新 (部分更新)
     */
    @Operation(summary = "根据id更新")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePatchBook(@PathVariable int id, @RequestBody Book updatedBook) {
        try {
            Book book = bookService.patchBook(id, updatedBook);
            return ResponseEntity.ok(book);
        } catch (NotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }




}

