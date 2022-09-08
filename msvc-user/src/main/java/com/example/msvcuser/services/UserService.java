package com.example.msvcuser.services;

import com.example.msvcuser.entities.User;
import com.example.msvcuser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Override
    public List<User> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.repository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return this.repository.save(user);
    }

    @Override
    public void delete(Long id) {
        this.repository.deleteById(id);
    }
}
