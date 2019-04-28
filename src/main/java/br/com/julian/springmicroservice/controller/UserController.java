package br.com.julian.springmicroservice.controller;

import br.com.julian.springmicroservice.model.User;
import br.com.julian.springmicroservice.service.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserDaoService userDaoService;

    @PostMapping("/users")
    public ResponseEntity<Object> save(@RequestBody User user) {

        User saved = userDaoService.save(user);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/users")
    public List<User> getAll() {
        return userDaoService.findAll();
    }

    @GetMapping("users/{id}")
    public User findOne(@PathVariable("id") Long id) {
        return userDaoService.findById(id);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userDaoService.deleteById(id);
    }


}
