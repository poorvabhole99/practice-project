package com.practice.practiceProject.service;

import com.practice.practiceProject.dto.BookDto;
import com.practice.practiceProject.dto.BookUpdateDto;
import com.practice.practiceProject.exception.PracticeProjectException;
import com.practice.practiceProject.response.BookResponse;
import com.practice.practiceProject.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface ShopOwnerService {
    BookResponse addBook(BookDto bookDto) throws PracticeProjectException;
    BookResponse getAllBook() throws PracticeProjectException;
    BookResponse updateBook(Integer id, BookUpdateDto bookUpdateDto) throws PracticeProjectException;

    BookResponse deleteBook(Integer id) throws PracticeProjectException;

    UserResponse getAllCustomers() throws PracticeProjectException;
}
