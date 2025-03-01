package com.rd.jdbc.opeartions.repository;

import com.rd.jdbc.opeartions.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RepoImpl extends UserRepository{

    @Autowired
    public RepoImpl(JdbcOperations jdbcOperations) {
        super(jdbcOperations);
    }

    @Override
    public <S extends User> S save(S entity) {
        return null;
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<User> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public Iterable<User> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
