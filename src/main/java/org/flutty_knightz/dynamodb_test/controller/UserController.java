package org.flutty_knightz.dynamodb_test.controller;

import lombok.RequiredArgsConstructor;
import org.flutty_knightz.dynamodb_test.entity.User;
import org.flutty_knightz.dynamodb_test.repository.UserRepository;
import org.flutty_knightz.dynamodb_test.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dynamodb_test/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    // CREATE User
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

//    -----------------------------------------------------------

    @GetMapping("/method/{userId}/{createdDate}")
    public ResponseEntity<User> method(@PathVariable int userId, @PathVariable String createdDate) {
        User user = userRepository.getById(userId,
                createdDate);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @GetMapping("/query/{id}/{name}")
    public ResponseEntity<List<User>> query(@PathVariable String name, @PathVariable int id) {
        List<User> user = userRepository.queryByName(id,
                name);
        return !user.isEmpty() ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @GetMapping("/scan")
    public ResponseEntity<List<User>> scan() {
        return ResponseEntity.ok(userRepository.scanAll());
    }

    @GetMapping("/lambda/{name}/{email}")
    public ResponseEntity<List<User>> lambda(@PathVariable String name, @PathVariable String email) {
        return ResponseEntity.ok(userRepository.scanByName(name,
                email));
    }

//    ----------------------------------------------------------------------

    // UPDATE User
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userRepository.update(user);
        return ResponseEntity.ok(user);
    }

    // DELETE User
    @DeleteMapping("delete/{userId}/{createdDate}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId, @PathVariable String createdDate) {
        userRepository.delete(userId,
                createdDate);
        return ResponseEntity.ok().build();
    }


}
