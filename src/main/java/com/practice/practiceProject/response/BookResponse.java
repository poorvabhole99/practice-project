package com.practice.practiceProject.response;

import com.practice.practiceProject.entities.Book;
import com.practice.practiceProject.entities.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BookResponse extends BaseResponse {
    public Book book;
    public List<Book> bookList;
    public List<User> userList;


    public BookResponse(String message, Boolean success) {
        super(message, success);
    }

    public BookResponse(Book book, String message, Boolean success) {
        super(message, success);
        this.book = book;
    }

    public BookResponse(List<Book> bookList, Boolean success) {
        super(success);
        this.bookList = bookList;
    }

    public BookResponse(Boolean success, List<User> userList) {
        super(success);
        this.userList = userList;
    }
}
