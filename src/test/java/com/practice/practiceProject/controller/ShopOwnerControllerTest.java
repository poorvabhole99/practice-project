package com.practice.practiceProject.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.practiceProject.Security.JwtAuthFilter;
import com.practice.practiceProject.TestContainerBase;
import com.practice.practiceProject.constant.BookConstant;
import com.practice.practiceProject.dto.BookDto;
import com.practice.practiceProject.dto.BookUpdateDto;
import com.practice.practiceProject.entities.Book;
import com.practice.practiceProject.entities.User;
import com.practice.practiceProject.exception.PracticeProjectException;
import com.practice.practiceProject.response.BookResponse;
import com.practice.practiceProject.response.UserResponse;
import com.practice.practiceProject.service.serviceImpl.ShopOwnerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@WithMockUser
@SpringBootTest
public class ShopOwnerControllerTest extends TestContainerBase {
    public static final String POST_ADD_BOOK = "/shopowner/addbook";
    public static final String PUT_UPDATE_BOOK = "/shopowner/updateBook";
    public static final String DELETE_BOOK = "/shopowner/deleteBook";
    public static final String GET_ALL_BOOKS = "/shopowner/allbooks";
    public static final String GET_ALL_CUSTOMERS = "/shopowner/allCustomers";

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @MockBean
    private ShopOwnerServiceImpl shopOwnerServiceImpl;


    @BeforeEach
    public void setUp() {
        try {
            if (null == mockMvc) {
                mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void invalidAddBook() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("");
        bookDto.setAuthorName("");
        bookDto.setPrice(null);
        bookDto.setRentalPrice(null);
        bookDto.setStock(null);

        mockMvc.perform(MockMvcRequestBuilders.post(POST_ADD_BOOK)
                        .content(objectMapper.writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value(BookConstant.SHORT_BOOK_TITLE))
                .andExpect(jsonPath("$.authorName").value(BookConstant.SHORT_AUTHOR_NAME))
                .andExpect(jsonPath("$.price").value(BookConstant.NULL_BOOK_PRICE))
                .andExpect(jsonPath("$.rentalPrice").value(BookConstant.NULL_RENTAL_PRICE))
                .andExpect(jsonPath("$.stock").value(BookConstant.NULL_BOOK_STOCK));
    }

    @Test
    void validAddBook() throws Exception {
        // Arrange
        BookDto bookDto = new BookDto();
        bookDto.setTitle("testBook");
        bookDto.setIsbn("123456");
        bookDto.setAuthorName("testAuthor");
        bookDto.setPrice(100.00);
        bookDto.setRentalPrice(80.00);
        bookDto.setStock(10);

        Book book = new Book();
        book.setId(1);
        book.setTitle("testBookNew");
        book.setAuthorName("testAuthor");
        book.setIsbn("123456");
        book.setPrice(100.00);
        book.setRentalPrice(80.00);
        book.setStock(10);

        final BookResponse bookResponse = new BookResponse(book,BookConstant.BOOK_ADDED_SUCCESS, true);

        when(shopOwnerServiceImpl.addBook(bookDto)).thenReturn(bookResponse);

        // Convert the expected BookResponse to JSON
        String expectedJson = objectMapper.writeValueAsString(bookResponse);

        // Print expected JSON
        System.out.println("Expected JSON: " + expectedJson);

        // Act
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(POST_ADD_BOOK)
                        .content(objectMapper.writeValueAsString(bookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        // Compare the JSON responses
        assertEquals(expectedJson, result.getResponse().getContentAsString());
    }

    @Test
    void invalid_updateBook() throws Exception {
        BookUpdateDto bookUpdateDto = new BookUpdateDto();
        bookUpdateDto.setTitle("");
        bookUpdateDto.setAuthorName("");
        bookUpdateDto.setPrice(null);
        bookUpdateDto.setRentalPrice(null);
        bookUpdateDto.setStock(null);

        Integer id = 1;

        mockMvc.perform(MockMvcRequestBuilders.put(PUT_UPDATE_BOOK)
                        .param("id", String.valueOf(id))
                        .content(objectMapper.writeValueAsString(bookUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.rentalPrice").value(BookConstant.NULL_RENTAL_PRICE))
                .andExpect(jsonPath("$.price").value(BookConstant.NULL_BOOK_PRICE))
                .andExpect(jsonPath("$.authorName").value(BookConstant.SHORT_AUTHOR_NAME))
                .andExpect(jsonPath("$.title").value(BookConstant.SHORT_BOOK_TITLE))
                .andExpect(jsonPath("$.stock").value(BookConstant.NULL_BOOK_STOCK));
    }

    @Test
    void validUpdateBook() throws Exception {
        BookUpdateDto bookUpdateDto = new BookUpdateDto();
        bookUpdateDto.setTitle("testBook");
        bookUpdateDto.setAuthorName("testAuthor");
        bookUpdateDto.setPrice(120.00);
        bookUpdateDto.setRentalPrice(100.00);
        bookUpdateDto.setStock(20);

        Integer id = 1;

        Book updatedBook = new Book();
        updatedBook.setId(1);
        updatedBook.setTitle("testBookNew");
        updatedBook.setAuthorName("testAuthor");
        updatedBook.setIsbn("123456");
        updatedBook.setPrice(100.00);
        updatedBook.setRentalPrice(80.00);
        updatedBook.setStock(10);
        final BookResponse bookResponse = new BookResponse(updatedBook, BookConstant.BOOK_UPDATE_SUCCESS, true);
        when(shopOwnerServiceImpl.updateBook(id, bookUpdateDto)).thenReturn(bookResponse);

        mockMvc.perform(MockMvcRequestBuilders.put(PUT_UPDATE_BOOK)
                        .param("id", String.valueOf(id))
                        .content(objectMapper.writeValueAsString(bookUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void validGetBooks() throws Exception {
        Book book = new Book();
        book.setId(1);
        book.setTitle("testBookNew");
        book.setAuthorName("testAuthor");
        book.setIsbn("123456");
        book.setPrice(100.00);
        book.setRentalPrice(80.00);
        book.setStock(10);

        List<Book> bookList = new ArrayList<>();
        bookList.add(book);
        BookResponse bookResponse = new BookResponse(bookList, true);
        when(shopOwnerServiceImpl.getAllBook()).thenReturn(bookResponse);

        mockMvc.perform(MockMvcRequestBuilders.get(GET_ALL_BOOKS)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.bookList[0].title").value(bookList.get(0).getTitle()));

    }

    @Test
    void deleteBook_positiveScenario() throws Exception {
        BookResponse bookResponse = new BookResponse(BookConstant.BOOK_DELETE_SUCCESS, true);
        when(shopOwnerServiceImpl.deleteBook(1)).thenReturn(bookResponse);
        System.out.println(bookResponse);
        mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_BOOK)
                        .param("id", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(BookConstant.BOOK_DELETE_SUCCESS))
                .andExpect(jsonPath("$.success").value(true));

    }

    @Test
    void getAllCustomers_positiveScenario() throws Exception {
        User user = new User();
        user.setId(1);
        user.setFirstName("testFirstName");
        user.setEmailId("test@gmail.com");
        user.setLastName("testLastName");
        user.setDateOfBirth("15/05/2000");
        user.setPassword("password");

        List<User> userList = new ArrayList<>();
        userList.add(user);

        UserResponse userResponse = new UserResponse(userList,true);
        when(shopOwnerServiceImpl.getAllCustomers()).thenReturn(userResponse);

        mockMvc.perform(MockMvcRequestBuilders.get(GET_ALL_CUSTOMERS)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.userList[0].emailId").value(userList.get(0).getEmailId()));
    }
}
