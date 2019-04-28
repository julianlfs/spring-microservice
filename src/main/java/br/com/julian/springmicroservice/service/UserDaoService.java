package br.com.julian.springmicroservice.service;

import br.com.julian.springmicroservice.exception.UserNotFound;
import br.com.julian.springmicroservice.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<>();

    private static Integer cont = 3;

    static {
        users.add(new User(1L, "Julian", LocalDate.of(1989, Month.MAY, 13)));
        users.add(new User(2L, "Maria", LocalDate.of(1992, Month.MAY, 13)));
        users.add(new User(3L, "José", LocalDate.of(1975, Month.MAY, 13)));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        cont++;
        user.setId(Long.valueOf(cont));
        users.add(user);

        return user;
    }

    public User findById(Long id) {
        return users.stream()
                .filter( (u) -> u.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new UserNotFound("user not found"));
    }


}
