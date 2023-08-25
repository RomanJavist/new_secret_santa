package com.example.secret_santa.controllers;

import com.example.secret_santa.models.Person;
import com.example.secret_santa.security.PersonDetails;
import com.example.secret_santa.services.PersonService;
import com.example.secret_santa.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    private final PersonService personService;
    private final PersonValidator personValidator;

    public MainController(PersonService personService, PersonValidator personValidator) {
        this.personService = personService;
        this.personValidator = personValidator;
    }



    @GetMapping("/index")
    public String index(){
        // Получаем объект аутентификации -> с помощью SpringContextHolder обращаемся к контексту и на нем вызываем метод аутентификации
        // Из сессии текущего пользователя получаем объект, который был положен в данную сессию после аутентификации пользователя.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        System.out.println(personDetails.getPerson());
        System.out.println("ID пользователя: " + personDetails.getPerson().getId());
        System.out.println("Email пользователя: " + personDetails.getPerson().getEmail());
        System.out.println("Пароль пользователя: " + personDetails.getPerson().getPassword());
        System.out.println(personDetails);
        return "/index";
    }

//    @GetMapping("/registration")
//    public String registration(Model model){
//        model.addAttribute("Person", new Person());
//        return "registration";
//    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("person") Person person){

        return "registration";
    }
    @PostMapping("/registration")
    public String resultRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()){
            return "registration";
        }
        personService.register(person);
        return "redirect:/index.html";
    }

}
