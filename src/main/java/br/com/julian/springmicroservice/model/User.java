package br.com.julian.springmicroservice.model;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class User {

    private Long id;

    @Size(min = 2, message = "Name should have at least 2 characters")
    private String name;

    @Past
    private LocalDate birth;

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
