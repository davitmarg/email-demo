package org.example.email_demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class SubscriberDTO {
    @Email(message = "Invalid email address")
    private String email;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    public SubscriberDTO() {
    }

    public SubscriberDTO(String email, String name) {
        this.email = email;
        this.name = name;
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

    @Override
    public String toString() {
        return "SubscriberDTO{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
