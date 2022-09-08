package com.example.msvcuser.services;

import com.example.msvcuser.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    User save(User user);

    void delete(Long id);

}
