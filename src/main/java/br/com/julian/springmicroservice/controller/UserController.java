package br.com.julian.springmicroservice.controller;

import br.com.julian.springmicroservice.model.User;
import br.com.julian.springmicroservice.service.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserController {

    @Autowired
    private UserDaoService userDaoService;

    @PostMapping("/users")
    public ResponseEntity<Object> save(@Valid @RequestBody User user) {

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
    public Resource<User> findOne(@PathVariable("id") Long id) {

        User user = userDaoService.findById(id);

        //EntityModel<User> model = new EntityModel<User>(user);
        //WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAll());
        //model.add(linkTo.withRel("all-users"));

        Resource<User> resource = new Resource<User>(user);
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAll());
        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userDaoService.deleteById(id);
    }


}
