package com.rd.jdbc.opeartions;

import com.rd.jdbc.opeartions.models.User;
import com.rd.jdbc.opeartions.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByNameAndAbout() {
        List<User> users = userRepository.findByNameAndAbout();
        System.out.println(users);
    }
}
