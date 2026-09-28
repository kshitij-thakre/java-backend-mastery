package com.telemedicine.model;

import com.telemedicine.util.ValidationUtil;

/**
 * Represents a System Administrator.
 * Demonstrates:
 * - Inheritance (IS-A User)
 * - Polymorphism via overridden getDisplayDetails()
 */
public class Admin extends User {

    private String department;

    public Admin(int id, String name, String email, String phone, String department) {
        super(id, name, email, phone, UserRole.ADMIN);
        ValidationUtil.requireNonBlank(department, "Department");
        this.department = department.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        ValidationUtil.requireNonBlank(department, "Department");
        this.department = department.trim();
    }

    @Override
    public String getDisplayDetails() {
        return String.format("Admin #%d: %s | Dept: %s | Email: %s",
                getId(), getName(), department, getEmail());
    }
}
