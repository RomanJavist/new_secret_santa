package com.example.secret_santa.config;

//import com.example.secret_santa.security.AuthenticationProvider;

import com.example.secret_santa.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    private final PersonDetailsService personDetailsService;

//   Отключаем хэширование паролей для тестирования
//    @Bean
//    public PasswordEncoder getPasswordEncode(){
//        return NoOpPasswordEncoder.getInstance();
//    }

    @Bean
    public PasswordEncoder getPasswordEncode(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //Конфигурируем работу Spring Security
//        http.csrf().disable();// отключаем защиту от межсайтовой подделки запросов
//        http.csrf().ignoringRequestMatchers(new AntPathRequestMatcher("/enterprises/**")); // чтобы обойти 403 запрет доступа
//        http.csrf().ignoringRequestMatchers(new AntPathRequestMatcher("/registration/**"));
//        http.csrf().ignoringRequestMatchers(new AntPathRequestMatcher("/authentication/**"));
        http
                .httpBasic()
                .and()
                .authorizeHttpRequests() // указываем, что все страницы должны быть защищены аутентификацией
                .requestMatchers("/admin").hasRole("ADMIN") // указываем, что страница /admin доступна лишь пользователю с ролью ADMIN
                .requestMatchers("/authentication", "/error", "/registration", "/resources/**", "/static/**", "css/**", "js/**", "img/**").permitAll()  // указываем, что не аутентифицированные пользователи могут зайти на перечисленные страницы. С помощью permitAll указываем, что неаутентифицированные пользователи могут заходить на следующие страницы
                .anyRequest().hasAnyRole("USER", "ADMIN")
                // .anyRequest().authenticated() указываем, что для всех остальных страниц необходимо вызывать метод authenticated(),который открывает форму аутентификации
                .and() // указываем, что дальше настраивается аутентификация и соединяем ее с настройкой доступа
                .formLogin().loginPage("/authentication") // указываем какой url запрос будет отправляться посетитель при заходе на защищенные страницы
                .loginProcessingUrl("/process_email") // указываем на какой адрес будет отправляться данные с формы. Нам уже не нужно будет создавать метод в контроллере и обрабатывать данные с формы. Мы задали url, который используется по умолчанию для обработки формы аутентификации с помощью Spring Security. SS будет ждать объект с формы аутентификации и затем сверять логин и пароль с данными в БД.
                .defaultSuccessUrl("/index", true) // Указываем на какой url нужно отправить пользователя после успешной аутентификации. Вторым аргументом указывается true, чтобы перенаправление шло в любом случае после успешной аутентификации
                .failureUrl("/authentication?error") // указываем куда необходимо направить пользователя в случае проваленной аутентификации. В запрос будет передан объект error, который будет проверяться на форме и при наличии данного объекта в запросе выводится сообщение "Неправильный логин или пароль"
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/authentication");
        return http.build();
    }

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncode());

    }
}
