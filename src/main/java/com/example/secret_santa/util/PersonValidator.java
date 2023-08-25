package com.example.secret_santa.util;

import com.example.secret_santa.models.Person;
import com.example.secret_santa.services.PersonService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class PersonValidator implements Validator {

    private final PersonService personService;

    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    // В данном методе указываем для какой модели предназначен валидатор
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(personService.findByEmail(person) != null){
            errors.rejectValue("email", "", "Данный email уже зарегистрирован в системе");
        }
    }
}
