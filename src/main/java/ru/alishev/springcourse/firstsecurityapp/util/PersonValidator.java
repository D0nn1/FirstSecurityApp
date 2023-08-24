package ru.alishev.springcourse.firstsecurityapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alishev.springcourse.firstsecurityapp.models.Person;
import ru.alishev.springcourse.firstsecurityapp.services.PersonalDetailService;


@Component
public class PersonValidator implements Validator {

    private final PersonalDetailService personalDetailService;

    @Autowired
    public PersonValidator(PersonalDetailService personalDetailService) {
        this.personalDetailService = personalDetailService;
    }

    // здесь указываем класс, для объектов которого будет использоваться валидатор
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    // используем уже реализованный нами PersonalDetailService, т.к. функционал именно такой, какой нам нужен
    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        try {
            personalDetailService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return; // все ок, пользователь не найден
        }
        errors.rejectValue("username", "person.username.exists");
    }
}
