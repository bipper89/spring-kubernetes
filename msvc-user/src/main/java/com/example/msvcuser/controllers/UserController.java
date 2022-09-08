package com.example.msvcuser.controllers;

import com.example.msvcuser.entities.User;
import com.example.msvcuser.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService service;


    @GetMapping("/")
    public ResponseEntity<?> index() {
        Map<String, Object> response = new HashMap<>();
        response.put("code", OK);
        response.put("message", "OK");
        response.put("data", this.service.findAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> user = this.service.findById(id);
        if (user.isPresent()) {
            response.put("code", OK.value());
            response.put("message", "OK");
            response.put("data", user);
            return ResponseEntity.ok(response);
        }
        response.put("code", NOT_FOUND.value());
        response.put("message", "User with " + id + " not found");
        response.put("data", user);
        return ResponseEntity.status(NOT_FOUND).body(response);
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
        if (this.service.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", "The user with email " + user.getEmail() + " already exists"));
        }
        if (result.hasErrors()) {
            return validate(result);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("code", CREATED.value());
        response.put("message", "User created successfully");
        response.put("data", this.service.save(user));
        return ResponseEntity.status(CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validate(result);
        }
        Optional<User> o = service.findById(id);
        if (o.isPresent()) {
            User userDB = o.get();
            if (!user.getEmail().equalsIgnoreCase(userDB.getEmail()) &&
                    this.service.findByEmail(user.getEmail()).isPresent()) {
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("error", "The user with email " + user.getEmail() + " already exists"));
            }
            userDB.setName(user.getName());
            userDB.setEmail(user.getEmail());
            userDB.setPassword(user.getPassword());
            return ResponseEntity.status(CREATED).body(this.service.save(userDB));
        }
        return ResponseEntity.status(NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<User> o = this.service.findById(id);
        if (o.isPresent()) {
            this.service.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.status(BAD_REQUEST).body(errors);
    }

}
