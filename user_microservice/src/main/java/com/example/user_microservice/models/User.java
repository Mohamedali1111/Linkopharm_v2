package com.example.user_microservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import java.util.Objects;

@Entity
public class User {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotEmpty(message = "First name is required")
    private String firstname;
    
    @NotEmpty(message = "Last name is required")
    private String lastname;
    
    @NotEmpty(message = "Username is required")
    private String username;
    
    @Email(message = "Invalid email format")
    @NotEmpty(message = "Email is required")

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    private String email;
    
    @NotEmpty(message = "Password is required")
    private String password;

    @NotEmpty(message = "Confirm password is required")
    private String confirmPassword;
    private String role = "user";

    public User() {
    }

    public User(int id, String firstname, String lastname, String username, String email, String password, String confirmPassword, String role) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.role = role;
    }

    // Getters and setters for role field
    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Other getters and setters remain unchanged


   

    public User(int id, String firstname, String lastname, String username, String email, String password, String confirmPassword ) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", username='" + getUsername() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", confirmPassword='" + getConfirmPassword() + "'" +
            ", role='" + getRole() + "'" +
            "}";
    }


     @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return id == user.id && Objects.equals(firstname, user.firstname)&& Objects.equals(lastname, user.lastname)&& Objects.equals(username, user.username)&& Objects.equals(email, user.email)&& Objects.equals(password, user.password)&& Objects.equals(confirmPassword, user.confirmPassword)&& Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, username, email, password,confirmPassword,role);
    }

    
}

