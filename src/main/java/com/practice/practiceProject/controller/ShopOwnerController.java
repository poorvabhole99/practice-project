package com.practice.practiceProject.controller;

import com.practice.practiceProject.annotation.TrackExecutionTime;
import com.practice.practiceProject.dto.BookDto;
import com.practice.practiceProject.dto.BookUpdateDto;
import com.practice.practiceProject.exception.PracticeProjectException;
import com.practice.practiceProject.response.BookResponse;
import com.practice.practiceProject.response.UserResponse;
import com.practice.practiceProject.service.ShopOwnerService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopowner")
@Log4j2
public class ShopOwnerController {
    @Autowired
    ShopOwnerService shopOwnerService;

    /**
     * GET /shopOwner/books
     * Get a list of all books.
     */
    @TrackExecutionTime
    @GetMapping("/allbooks")
    public ResponseEntity<BookResponse> getAllBooks() throws PracticeProjectException {
        return ResponseEntity.ok(this.shopOwnerService.getAllBook());
    }


    /**
     * POST /shopOwner/books
     * Add a new book.
     */
    @TrackExecutionTime
    @PostMapping("/addbook")
    public ResponseEntity<BookResponse> addBook(@Valid @RequestBody BookDto bookDto) throws PracticeProjectException {
        final BookResponse bookResponse = this.shopOwnerService.addBook(bookDto);
        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }


    /**
     * PUT /shopOwner/books/{id}
     * Update a book's information.
     */
    @TrackExecutionTime
    @PutMapping("/updateBook")
    public ResponseEntity<BookResponse> updateBook(@RequestParam Integer id, @Valid @RequestBody BookUpdateDto bookUpdateDto) throws PracticeProjectException {
        return ResponseEntity.ok(this.shopOwnerService.updateBook(id, bookUpdateDto));
    }

    /**
     * DELETE /shopOwner/books/{id}
     * Delete a book.
     */
    @TrackExecutionTime
    @DeleteMapping("/deleteBook")
    public ResponseEntity<BookResponse> deleteBook(@RequestParam Integer id) throws PracticeProjectException {
        return ResponseEntity.ok(this.shopOwnerService.deleteBook(id));
    }


    /**
     * GET /shopOwner/customers
     * Get a list of all customers.
     */
    @TrackExecutionTime
    @GetMapping("/allCustomers")
    public ResponseEntity<UserResponse> getAllCustomers() throws PracticeProjectException {
        return ResponseEntity.ok(this.shopOwnerService.getAllCustomers());
    }
}
