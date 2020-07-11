package com.netposapi.client.service.impl;

import java.util.Optional;

import com.netposapi.client.models.Person;
import com.netposapi.client.models.request.JwtRequest;


public interface PersonServiceImpl {
    public abstract Optional<Person>  getPerson(int id);
    public abstract Person savePerson(JwtRequest personRequest);
    public abstract boolean emailExist (String email);
    public abstract boolean checkPassword(JwtRequest personRequest);
    public abstract Optional<Person> getPersonEmail(String email);

}