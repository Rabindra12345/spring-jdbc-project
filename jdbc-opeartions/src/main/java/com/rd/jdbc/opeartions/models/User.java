package com.rd.jdbc.opeartions.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.relational.core.mapping.Table;

//@Enti
//@Table(name = ="user",schema="public")
//@Entity
public class User {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String id;

    String name;

    String email;

    String about;

    public User(String id, String name, String email, String about) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.about = about;
    }

    public User() {
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id +
                ", name='" + name +
                ", email='" + email +
                ", about='" + about +
                '}';
    }
}
