package com.netposapi.client.service;

import java.util.ArrayList;
import java.util.Optional;

import com.netposapi.client.models.Person;
import com.netposapi.client.models.request.JwtRequest;
import com.netposapi.client.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Person> person = personRepository.findByUserName(username);
		if (person == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email inexistente em nossa base : " + username,null);
		}
		return new org.springframework.security.core.userdetails.User(person.get().getUserName(), person.get().getPassword(),
				new ArrayList<>());
	}
	
	public Person save(JwtRequest personRequest) {
		if(personRepository.existsByUserName(personRequest.getUsername())){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email inexistente em nossa base : " + personRequest.getUsername(),null);
		}
		Person newPerson = new Person();
		newPerson.setUserName(personRequest.getUsername());
		newPerson.setPassword(bcryptEncoder.encode(personRequest.getPassword()));
		return personRepository.save(newPerson);
	}
}