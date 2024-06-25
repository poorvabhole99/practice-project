package com.practice.practiceProject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertTrue;

@SpringBootTest
class PracticeProjectApplicationTests extends TestContainerBase{

    @Test
    void contextLoads(){
        assertTrue(true);
    }
}
