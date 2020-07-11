package com.netposapi.client.service;

import java.util.List;
import java.util.Optional;
import com.netposapi.client.models.Person;
import com.netposapi.client.models.request.JwtRequest;
import com.netposapi.client.repository.PersonRepository;
import com.netposapi.client.service.impl.PersonServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PersonService implements PersonServiceImpl {

    @Autowired
    PersonRepository personRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<Person> list() {
        return personRepository.findAll(); 
    }

    @Override
    public List<Person> search(String key) {
        return personRepository.findByUserNameStartingWith(key); 
    }

    @Override
    public Optional<Person> getPersonEmail(String email) {
        if(emailExist(email)){
            return personRepository.findByUserName(email);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Email não cadastrado", null);
    }
    

    @Override
    public Person savePerson(JwtRequest personRequest)  {
        if(emailExist(personRequest.getUsername())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Email já cadastrado", null);
        }
        Person person = new Person();
        person.setUserName(personRequest.getUsername());
        person.setPassword(passwordEncoder.encode(personRequest.getPassword()));     
        return  personRepository.save(person);
    }

    @Override
    public boolean emailExist(String email) {      
        return true;// personRepository.existsByUserName(email);
    }

    @Override
    public boolean checkPassword(JwtRequest personRequest)  {
        if(!emailExist(personRequest.getUsername())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Email não cadastrado", null);
        }
        Optional<Person> person = personRepository.findById(1);
        if(!passwordEncoder.matches(personRequest.getPassword(), person.get().getPassword())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Password/Email Inválido", null);
        }

        return true;
        
    }


}