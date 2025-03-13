package org.example.email_demo.model;

public class RepoActivity {
    private String id; // Commit SHA or Pull Request number
    private String message; // Commit message or Pull Request title
    private String type; // "commit" or "pull_request"
    private String timestamp; // Date of commit or pull request creation
    private String state; // Pull request state ("open", "closed", etc.)
    private String repositoryName; // Name of the repository
    private String username; // Username of the repository owner

    // Constructor for commits
    public RepoActivity(String id, String message, String timestamp) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
        this.type = "commit";
    }

    // Constructor for pull requests
    public RepoActivity(String id, String message, String timestamp, String state) {
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
        this.state = state;
        this.type = "pull_request";
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
