package com.practice.practiceProject.TestNG;

import com.practice.practiceProject.TestContainerBase;
import com.practice.practiceProject.exception.PracticeProjectException;
import com.practice.practiceProject.repository.BookRepository;
import com.practice.practiceProject.service.ShopOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;

import static org.mockito.Mockito.when;

public class ShopOwnerServiceTest extends TestContainerBase {

    @Autowired
    private ShopOwnerService shopOwnerService;
    @MockBean
    private BookRepository bookRepository;

    void getAllBook_whenListIsEmpty() throws PracticeProjectException {

        when(bookRepository.findAll()).thenReturn(Collections.emptyList());
        PracticeProjectException practiceProjectException = shopOwnerService.getAllBook();
    }


}
