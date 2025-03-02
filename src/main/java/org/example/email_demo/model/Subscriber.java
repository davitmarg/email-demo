package org.example.email_demo.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public class Subscriber {
    @Email
    @NotEmpty
    private String email;
    private String name;
    private String ipAddress;
    private LocalDateTime creationTimestamp;

    public Subscriber() {
    }

    public Subscriber(String email, String name, String ipAddress, LocalDateTime creationTimestamp) {
        this.email = email;
        this.name = name;
        this.ipAddress = ipAddress;
        this.creationTimestamp = creationTimestamp;
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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", creationTimestamp='" + creationTimestamp + '\'' +
                '}';
    }
}
