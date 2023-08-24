package ru.alishev.springcourse.firstsecurityapp.services;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alishev.springcourse.firstsecurityapp.dao.PeopleReposetory;
import ru.alishev.springcourse.firstsecurityapp.models.Person;


@Service
public class RegistrationService {
    private final PeopleReposetory peopleReposetory;

    @Autowired
    public RegistrationService(PeopleReposetory peopleReposetory) {
        this.peopleReposetory = peopleReposetory;
    }

    @Transactional
    public void register(Person person) {
        peopleReposetory.save(person);
    }
}
