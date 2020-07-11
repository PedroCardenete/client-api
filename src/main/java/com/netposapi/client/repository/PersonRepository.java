package com.netposapi.client.repository;

import java.util.Optional;

import com.netposapi.client.models.Person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>{
    boolean existsByUserName(String username);
    Optional<Person> findById(Integer id);
    Optional<Person> findByUserName(String username);
}