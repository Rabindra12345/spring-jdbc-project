package com.rd.jdbc.opeartions.controller;

import com.rd.jdbc.opeartions.models.User;
import com.rd.jdbc.opeartions.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class TestController {

    private UserRepository userRepository;

    @Autowired
    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/user")
    public ResponseEntity<?> getUsers() {
//        return this.userRepository.findByNameAndAbout();
        return ResponseEntity.ok(this.userRepository.findByNameAndAbout());
    }
}
