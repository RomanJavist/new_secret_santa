package com.example.secret_santa.models;

import com.example.secret_santa.enums.Gender;
import com.example.secret_santa.enums.Role;
import com.example.secret_santa.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "person")
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String full_name;
    private String username;
    @NotEmpty(message = "Пароль не может быть пустым")
//    @Size(min = 5, max = 100, message = "Пароль не может быть короче 5 и длиннее 100 символов")
    @Column(name="password")
//    @Pattern(regexp = )
    private String password;
    @NotEmpty(message = "Email не может быть пустым")
    @Size(min = 5, max = 100, message = "Email не может быть короче 5 и длиннее 100 символов")
    @Pattern(regexp =  "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Введите корректный email")
    @Column(name="email")
    private String email;
    private int telephone;
    private String about;
    private Status status;
    private Gender gender;
    @Column(name = "role")
    private Role role;
    private Date date_of_birth;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(email, person.email) && Objects.equals(password, person.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }
}
