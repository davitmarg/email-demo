package org.example.email_demo.model;

// Create a model class to represent subscriber information
//Implement an in-memory repository to store submissions
//Store at minimum:
//Email address (String)
//Creation timestamp (LocalDateTime)
//IP address (String)

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class Subscriber {
    @Email
    @NotEmpty
    private String email;
    private String name;
    private String ipAddress;
    private String creationTimestamp;

    public Subscriber() {
    }

    public Subscriber(String email, String name, String ipAddress, String creationTimestamp) {
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

    public String getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(String creationTimestamp) {
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
