package ru.alishev.springcourse.firstsecurityapp.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.alishev.springcourse.firstsecurityapp.security.PersonDetails;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {

        return "hello";
    }


    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        /*
        Здесь мы получаем доступ к нашему объекту из потока, достаем пользователя из текущего потока.
        Мы достаем объект Authentication, который получлся из authProvider.authenticate() и был положен в сессию.
        А spring security достал его из сессии поместил в контекст <.getContext()> и уже с помощью
        .getAuthentication() получили authentication.
        */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        System.out.println(personDetails.getPerson());

        return "hello";
    }

}

