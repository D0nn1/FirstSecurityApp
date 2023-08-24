package ru.alishev.springcourse.firstsecurityapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.alishev.springcourse.firstsecurityapp.dao.PeopleReposetory;
import ru.alishev.springcourse.firstsecurityapp.models.Person;
import ru.alishev.springcourse.firstsecurityapp.security.PersonDetails;

import java.util.Optional;

/* Прослойка между dao и **.
Сервис не свосем обычный, предназначен именно для Spring Security.
Чтобы Spring Security знал, что этот сервис загружает пользователя, мы implements UserDetailsService.
 */
@Service
public class PersonalDetailService implements UserDetailsService {
    private final PeopleReposetory peopleReposetory;

    @Autowired
    public PersonalDetailService(PeopleReposetory peopleReposetory) {
        this.peopleReposetory = peopleReposetory;
    }

    // Даем понять спрингу, что этот сервис загружает по имени пользователя
    // Возвращаем человека по имени
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = peopleReposetory.findByUsername(username);

        if (person.isEmpty()) {
            throw new UsernameNotFoundException("User: \"" + username + "\" not found");
        }

        // вообще, может быть возвращен любой объект, класс которого реализует UserDetails
        return new PersonDetails(person.get());
    }
}
