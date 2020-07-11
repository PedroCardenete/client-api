package com.netposapi.client.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.netposapi.client.config.JwtTokenUtil;
import com.netposapi.client.models.Person;
import com.netposapi.client.models.request.JwtRequest;
import com.netposapi.client.models.request.JwtResponse;
import com.netposapi.client.models.request.ResponseCustomized;
import com.netposapi.client.service.JwtUserDetailsService;
import com.netposapi.client.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/person")
public class PersonController {

  @Autowired
  PersonService personService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private JwtUserDetailsService userDetailsService;

  @ApiOperation(value = "Retorna Person Logado")
  @GetMapping
  public ResponseEntity<Object> find() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Optional<Person> person = personService.getPersonEmail(authentication.getName());
    return ResponseEntity.ok().body(person);
  }

  @ApiOperation(value = "Retorna Person Logado")
  @PostMapping(value = "/register")
  public ResponseEntity<Object> find(@Valid @RequestBody JwtRequest personRequest) {
    Person person = userDetailsService.save(personRequest);
    return ResponseEntity.ok().body(ResponseCustomized.response("Sucess", person));
  }

  @ApiOperation(value = "Autenticação Person")
  @PostMapping(value = "/authenticate")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

    authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

    final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

    final String token = jwtTokenUtil.generateToken(userDetails);

    return ResponseEntity.ok(new JwtResponse("Bearer " + token));
  }

  private void authenticate(String username, String password) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new Exception("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new Exception("INVALID_CREDENTIALS", e);
    }

  }

  @ApiOperation(value = "Listar Person")
  @GetMapping(value = "/list")
  public ResponseEntity<Object> list() {
    List<Person> person = personService.list();
    return person.size() > 0 ? ResponseEntity.ok().body(ResponseCustomized.response("Sucess", person)) 
        : ResponseEntity.badRequest().body("Não existe nenhum person cadastrado");
  }

  @ApiOperation(value = "Listar Person")
  @GetMapping(value = "/search/{key}")
  public ResponseEntity<Object> search(@PathVariable String key) {
    List<Person> person = personService.search(key);
    return person.size() > 0 ? ResponseEntity.ok().body(ResponseCustomized.response("Sucess", person))  
        : ResponseEntity.badRequest().body("Não existe nenhum person cadastrado com esse parametro de pesquisa");
  }

}