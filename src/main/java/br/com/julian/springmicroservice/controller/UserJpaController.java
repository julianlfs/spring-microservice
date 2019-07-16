package br.com.julian.springmicroservice.controller;

import br.com.julian.springmicroservice.model.Post;
import br.com.julian.springmicroservice.model.User;
import br.com.julian.springmicroservice.repository.PostRepository;
import br.com.julian.springmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserJpaController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @PostMapping("jpa/users")
    public ResponseEntity<Object> save(@Valid @RequestBody User user) {

        User saved = userRepository.save(user);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("jpa/users")
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("jpa/users/{id}")
    public Resource<User> findOne(@PathVariable("id") Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("nao encontrado"));

        //EntityModel<User> model = new EntityModel<User>(user);
        //WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAll());
        //model.add(linkTo.withRel("all-users"));

        Resource<User> resource = new Resource<User>(user);
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAll());
        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    @DeleteMapping("jpa/users/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
    }

    @GetMapping("jpa/users/{id}/posts")
    public List<Post> getAllPostFromUser(@PathVariable("id") Long id) {
        Optional<User> byId = userRepository.findById(id);

        if (!byId.isPresent())
            throw new RuntimeException("not found");

        return byId.get().getPosts();
    }

    @PostMapping("jpa/users/{id}/posts")
    public ResponseEntity<Object> savePost(@PathVariable Long id, @Valid @RequestBody Post post) {

        Optional<User> byId = userRepository.findById(id);

        if (!byId.isPresent()) throw new RuntimeException("not found");

        User user = byId.get();

        post.setUser(user);

        postRepository.save(post);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

}
