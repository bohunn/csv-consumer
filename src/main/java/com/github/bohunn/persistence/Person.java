package com.github.bohunn.persistence;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Cacheable
public class Person {
    @Id
    @GeneratedValue
    public UUID id;
    public String firstName;
    public String lastName;
    public String email;
    public String gender;
    public String ipAddress;

    public Person (String firstName, String lastName, String email, String gender, String ipAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.ipAddress = ipAddress;
    }

//    public static Uni<Person> findByName(String firstName) {
//        return find("name", firstName).firstResult();
//    }


}
