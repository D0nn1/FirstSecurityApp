package ru.alishev.springcourse.firstsecurityapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.firstsecurityapp.models.Person;

import java.util.Optional;

@Repository
public interface PeopleReposetory extends JpaRepository<Person, Integer> {
    Optional<Person> findByUsername(String username);
}
