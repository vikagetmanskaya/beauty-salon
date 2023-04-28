package com.example.diploma.entity;

import com.example.diploma.util.ValidConstant;

import javax.validation.constraints.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "username")
    @Pattern(regexp = ValidConstant.USERNAME_PATTERN)
    private String username;
    @Column(name = "email")
    @Pattern(regexp = ValidConstant.EMAIL_PATTERN)
    private String email;
    @Column(name = "password")
    @Size(min = 8)
    private String password;
    @Column(name = "first_name")
    @Pattern(regexp = ValidConstant.FIRST_AND_LAST_NAME_PATTERN)
    private String firstName;
    @Column(name = "last_name")
    @Pattern(regexp = ValidConstant.FIRST_AND_LAST_NAME_PATTERN)
    private String lastName;
    @Column(name = "phone_number")
    @Pattern(regexp = ValidConstant.PHONE_NUMBER_PATTERN)
    private String phoneNumber;
    @Column(name = "enabled")
    private boolean enabled;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public User() {
    }

    public User(int id, String username, String password, String firstName, String lastName, String phoneNumber, boolean enabled, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.enabled = enabled;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
