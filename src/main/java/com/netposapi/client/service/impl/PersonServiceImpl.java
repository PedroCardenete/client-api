package com.netposapi.client.service.impl;

import java.util.List;
import java.util.Optional;

import com.netposapi.client.models.Person;
import com.netposapi.client.models.request.JwtRequest;


public interface PersonServiceImpl {
    public abstract List<Person>  list();
    public abstract Person savePerson(JwtRequest personRequest);
    public abstract boolean emailExist (String email);
    public abstract boolean checkPassword(JwtRequest personRequest);
    public abstract Optional<Person> getPersonEmail(String email);
    public abstract List<Person> search(String key);

}