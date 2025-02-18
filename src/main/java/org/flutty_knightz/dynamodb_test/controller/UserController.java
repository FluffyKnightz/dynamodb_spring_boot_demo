package org.flutty_knightz.dynamodb_test.controller;

import lombok.RequiredArgsConstructor;
import org.flutty_knightz.dynamodb_test.entity.User;
import org.flutty_knightz.dynamodb_test.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dynamodb_test/api/v1/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // CREATE User
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.ok(user);
    }

    // READ a User by userId and createdDate
    @GetMapping("/{userId}/{createdDate}")
    public ResponseEntity<User> getUser(@PathVariable int userId, @PathVariable String createdDate) {
        User user = userService.getUser(userId, createdDate);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    // UPDATE User
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseEntity.ok(user);
    }

    // DELETE User
    @DeleteMapping("/{userId}/{createdDate}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId, @PathVariable String createdDate) {
        userService.deleteUser(userId, createdDate);
        return ResponseEntity.ok().build();
    }

    // GET all Users
    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
