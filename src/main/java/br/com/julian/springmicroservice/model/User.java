package br.com.julian.springmicroservice.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@ApiModel(description = "all details abou the user")
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 2, message = "Name should have at least 2 characters")
    private String name;

    @Past
    @ApiModelProperty(notes = "Cannot be in the future")
    private LocalDate birth;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public User(@Size(min = 2, message = "Name should have at least 2 characters") String name, @Past LocalDate birth, List<Post> posts) {
        this.name = name;
        this.birth = birth;
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public User() {
    }

    public User(Long id, String name, LocalDate birth) {
        this.id = id;
        this.name = name;
        this.birth = birth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }
}
