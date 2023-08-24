package ru.alishev.springcourse.firstsecurityapp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.alishev.springcourse.firstsecurityapp.models.Person;

import java.util.Collection;


/*
Вместо того, чтобы реализовывать методы в классе Person,
мы создаем некий класс обертку PersonDetails для работы с Person.
Методы в Person могут называться как угодно, но здесь, за счет интерфейса,
для поддержания стандарта, будут именно такие методы.
*/
public class PersonDetails implements UserDetails {

    private final Person person;

    public Person getPerson() {
        return person;
    }


    //нужно, чтобы получить доступ к полям person-а после успешной аутентификации
    public PersonDetails(Person person) {
        this.person = person;
    }

    @Override // возвращает Collection<? extends GrantedAuthority> - список прав человека
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
