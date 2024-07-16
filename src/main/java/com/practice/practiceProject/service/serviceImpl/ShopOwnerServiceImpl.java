package com.practice.practiceProject.service.serviceImpl;

import com.practice.practiceProject.constant.BookConstant;
import com.practice.practiceProject.dto.BookDto;
import com.practice.practiceProject.dto.BookUpdateDto;
import com.practice.practiceProject.entities.Book;
import com.practice.practiceProject.entities.User;
import com.practice.practiceProject.enums.ErrorEnum;
import com.practice.practiceProject.enums.RoleEnum;
import com.practice.practiceProject.exception.PracticeProjectException;
import com.practice.practiceProject.helper.BookHelper;
import com.practice.practiceProject.repository.BookRepository;
import com.practice.practiceProject.repository.UserRepository;
import com.practice.practiceProject.response.BookResponse;
import com.practice.practiceProject.response.ErrorResponse;
import com.practice.practiceProject.response.UserResponse;
import com.practice.practiceProject.service.ShopOwnerService;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ShopOwnerServiceImpl implements ShopOwnerService {

  private final BookRepository bookRepository;
  private final UserRepository userRepository;

  public ShopOwnerServiceImpl(BookRepository bookRepository, UserRepository userRepository) {
    this.bookRepository = bookRepository;
    this.userRepository = userRepository;
  }


  @Override
  public BookResponse addBook(final BookDto bookDto) throws PracticeProjectException {
    log.debug("Add book method started");
    validateBookTitle(bookDto.getTitle());
    log.info("Add book method completed");
    return new BookResponse(this.bookRepository.save(mapBookDetails(bookDto)),
        BookConstant.BOOK_ADDED_SUCCESS, true);
  }

  private void validateBookTitle(String bookTitle) throws PracticeProjectException {
    log.debug("Validate book title {}", bookTitle);
    if (this.bookRepository.existsByTitle(bookTitle)) {
      log.error("Book with given title '{}' is already exists", bookTitle);
      throw new PracticeProjectException(
          new ErrorResponse(ErrorEnum.BOOK_ALREADY_EXISTS.getErrorMsg(), false,
              ErrorEnum.BOOK_ALREADY_EXISTS.getErrorCode()));
    }
  }

  private Book mapBookDetails(BookDto bookDto) {
    final Book book = new Book();

    book.setAuthorName(bookDto.getAuthorName());
    book.setTitle(bookDto.getTitle());
    book.setPrice(bookDto.getPrice());
    book.setStock(bookDto.getStock());
    book.setRentalPrice(bookDto.getRentalPrice());
    book.setIsbn(BookHelper.generateIsbn());

    return book;
  }

  @Override
  public BookResponse getAllBook() throws PracticeProjectException {
    log.debug("Get all books method started");
    final List<Book> bookList = this.bookRepository.findAll();
    if (bookList.isEmpty()) {
      log.error("Empty book list");
      throw new PracticeProjectException(
          new ErrorResponse(ErrorEnum.EMPTY_BOOK_LIST.getErrorMsg(), false,
              ErrorEnum.EMPTY_BOOK_LIST.getErrorCode()));
    }
    log.info("Get all books method completed");
    return new BookResponse(bookList, true);
  }

  @Override
  public BookResponse updateBook(Integer id, BookUpdateDto bookUpdateDto)
      throws PracticeProjectException {
    log.debug("Update book with id {} method starts ", id);
    final Optional<Book> optionalBook = this.bookRepository.findById(id);
    Book book;
    Book updatedBook;
    if (optionalBook.isEmpty()) {
      log.error("Book with id {} not found", id);
      throw new PracticeProjectException(
          new ErrorResponse(ErrorEnum.BOOK_NOT_FOUND.getErrorMsg(), false,
              ErrorEnum.BOOK_NOT_FOUND.getErrorCode()));
    } else {
      book = optionalBook.get();
      updatedBook = mapUpdateBook(book, bookUpdateDto);
      this.bookRepository.save(updatedBook);
    }
    log.info("Update book with id {} method completed", id);
    return new BookResponse(updatedBook, BookConstant.BOOK_UPDATE_SUCCESS, true);
  }

  private Book mapUpdateBook(Book book, BookUpdateDto bookUpdateDto) {

    book.setAuthorName(bookUpdateDto.getAuthorName());
    book.setTitle(bookUpdateDto.getTitle());
    book.setPrice(bookUpdateDto.getPrice());
    book.setStock(bookUpdateDto.getStock());
    book.setRentalPrice(bookUpdateDto.getRentalPrice());

    return book;
  }

  @Override
  public BookResponse deleteBook(Integer id) throws PracticeProjectException {
    log.debug("Delete book with id {} method started", id);
    final Optional<Book> optionalBook = this.bookRepository.findById(id);
    Book book;
    if (optionalBook.isPresent()) {
      book = optionalBook.get();
      book.setStock(0);
      this.bookRepository.save(book);
    } else {
      log.error("Delete book with id {} not found", id);
      throw new PracticeProjectException(
          new ErrorResponse(ErrorEnum.BOOK_NOT_FOUND.getErrorMsg(), false,
              ErrorEnum.BOOK_NOT_FOUND.getErrorCode()));
    }
    log.info("Delete book with id {} method completed", id);
    return new BookResponse(BookConstant.BOOK_DELETE_SUCCESS, true);
  }

  @Override
  public UserResponse getAllCustomers() throws PracticeProjectException {
    List<User> userList = this.userRepository.findAllUserByRole(RoleEnum.CUSTOMER.getValue());
    if (userList.isEmpty()) {
      throw new PracticeProjectException(
          new ErrorResponse(ErrorEnum.EMPTY_USER_LIST.getErrorMsg(), false,
              ErrorEnum.EMPTY_USER_LIST.getErrorCode()));
    }
    return new UserResponse(userList, true);
  }
}
