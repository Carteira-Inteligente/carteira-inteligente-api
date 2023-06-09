package br.com.carteirainteligente.api.controller;

import br.com.carteirainteligente.api.model.User;
import br.com.carteirainteligente.api.service.UserService;
import br.com.carteirainteligente.api.validator.UserValidator;
import kong.unirest.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/bombou")
    public ResponseEntity<String> bombou() {
        String bombou = "Bombou";
        return ResponseEntity.status(HttpStatus.CREATED).body(bombou);
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.listUsers();
        return users.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(users) : ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User user, BindingResult result) {
        userValidator.validate(user, result);
        userValidator.validateSave(user, result);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user, BindingResult result) {
        userValidator.validate(user, result);
        userValidator.validateUpdate(user, id, result);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        User updatedUser = userService.updateUser(id, user);
        return updatedUser == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id/*, @RequestBody User user,BindingResult result*/) {
        /*userValidator.validateDelete(id, result);
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }*/
        User deletedUser = userService.deleteUser(id);
        return deletedUser == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(deletedUser);
    }
}