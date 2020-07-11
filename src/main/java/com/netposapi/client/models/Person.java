package com.netposapi.client.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="PERSON")
public class Person {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "username")
    private String userName;

    @Column
    private String password;
    
    @Column
    private String roles;


    public Person(Integer Id, String userName, String password, String roles) {
        this.Id = Id;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    public Integer getId() {
        return this.Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String Username) {
        this.userName = Username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return this.roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Person() {
    }

}