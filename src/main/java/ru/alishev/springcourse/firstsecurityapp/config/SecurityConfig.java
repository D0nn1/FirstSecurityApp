package ru.alishev.springcourse.firstsecurityapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.alishev.springcourse.firstsecurityapp.services.PersonalDetailService;

//конфигурация Spring Security
//главный класс для настройки Spring Security
@Configuration
@EnableWebSecurity // аннотация дает понять, что это конфиг класс Spring Security
public class SecurityConfig {
    private final PersonalDetailService personalDetailService;

    @Autowired
    public SecurityConfig(PersonalDetailService personalDetailService) {
        this.personalDetailService = personalDetailService;
    }

    //настраивает аутентификацию
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(personalDetailService);
    }

    // говорим спрингу о том, что не шифруем пароль
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


    // конфигурируем spring security (какия стр отвечает зв вход, какая за ошибки и т.д.)
    // в этом же методе конфигурируем авторизацию (доступ пользователя к страницам на основании их статуса)
    // в этот метод поступает http запрос и мы смотрим от кого этот запрос и т.п.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /* Все, что ниже - называется правилами авторизации. Читаются сверху вниз.
        Поэтому необходимо писать от более специфичных к более общим.
        Когда приходит запрос, то применяется первый подошедший mathcer */
        http
                .csrf().disable()
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/auth/login", "/error", "/auth/registration")
                        .permitAll()

                        //на все остальные страницы не пускаем неавторизованных
                        .anyRequest()
                        .authenticated()

                )
                .formLogin((form) -> form
                        .loginPage("/auth/login")

                        // указываем на какой адрес будет отправляться заполненная форма, чтобы spring security
                        // уже сам сравнил с бд имя и пароль
                        .loginProcessingUrl("/process_login")

                        //в случае успешной аутентификации ВСЕГДА (благодаря true) будет перенаправление на /hello
                        .defaultSuccessUrl("/hello", true)

                        .failureUrl("/auth/login?error")
                );
        return http.build();
    }


}
