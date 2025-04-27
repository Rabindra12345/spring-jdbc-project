package com.rd.jdbc.opeartions.controller;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.rd.jdbc.opeartions.UserAdditionService;
import com.rd.jdbc.opeartions.models.User;
import com.rd.jdbc.opeartions.repository.UserElasticsearchRepository;
import com.rd.jdbc.opeartions.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
public class TestController {

    private UserRepository userRepository;

    private UserAdditionService userAdditionService;

    private final UserElasticsearchRepository userElasticsearchRepository;

    @Autowired
    public TestController(UserRepository userRepository, UserElasticsearchRepository userElasticsearchRepository, UserAdditionService userAdditionService) {
        this.userRepository = userRepository;
        this.userElasticsearchRepository = userElasticsearchRepository;
        this.userAdditionService = userAdditionService;
    }


    @GetMapping("/user")
    public ResponseEntity<?> getUsers() {
//        return this.userRepository.findByNameAndAbout();
        return ResponseEntity.ok(this.userRepository.findByNameAndAbout());
    }

    @GetMapping("users")
    public ResponseEntity<?> searchUsers(@RequestParam String keyword,@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(userElasticsearchRepository.searchUsers(keyword,page,size));
    }


    @PostMapping("users")
    public ResponseEntity<?> addUsers(@RequestBody User user) {
        return ResponseEntity.ok(userElasticsearchRepository.saveUser(user));
    }

    @PostMapping("users/add")
    public ResponseEntity<?> bulkUserAdd() {
        return ResponseEntity.ok(userAdditionService.bulkInsertUsers(100000));
    }


}
