package com.telemedicine.model;

import com.telemedicine.util.ValidationUtil;

/**
 * Abstract base class representing a generic user in the system.
 * Demonstrates:
 * - Abstract Class & Abstract Methods
 * - Encapsulation (private fields, validation in getters/setters)
 * - Final fields for immutable entity identity
 * - Constructor validation & 'this' keyword
 * - Method Overloading (Compile-time polymorphism)
 */
public abstract class User {

    private final int id;
    private String name;
    private String email;
    private String phone;
    private final UserRole role;

    // Parameterized constructor
    public User(int id, String name, String email, String phone, UserRole role) {
        ValidationUtil.validatePositive(id, "User ID");
        ValidationUtil.requireNonBlank(name, "Name");
        ValidationUtil.validateEmail(email);
        ValidationUtil.requireNonBlank(phone, "Phone");

        if (role == null) {
            throw new IllegalArgumentException("User role cannot be null");
        }

        this.id = id;
        this.name = name.trim();
        this.email = email.trim();
        this.phone = phone.trim();
        this.role = role;
    }

    // Overloaded constructor (Constructor Chaining)
    public User(int id, String name, String email, UserRole role) {
        this(id, name, email, "N/A", role);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        ValidationUtil.requireNonBlank(name, "Name");
        this.name = name.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        ValidationUtil.validateEmail(email);
        this.email = email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        ValidationUtil.requireNonBlank(phone, "Phone");
        this.phone = phone.trim();
    }

    public UserRole getRole() {
        return role;
    }

    // Method Overloading (Compile-time Polymorphism)
    public void updateContact(String newEmail) {
        setEmail(newEmail);
    }

    public void updateContact(String newEmail, String newPhone) {
        setEmail(newEmail);
        setPhone(newPhone);
    }

    /**
     * Abstract method to be implemented by specific user subtypes.
     * Demonstrates dynamic/runtime polymorphism.
     */
    public abstract String getDisplayDetails();

    @Override
    public String toString() {
        return String.format("[%s] ID: %d | Name: %s | Email: %s | Phone: %s",
                role, id, name, email, phone);
    }
}
