package org.example.email_demo.dto;

import jakarta.validation.constraints.NotEmpty;

public class GithubRepoDTO {
    @NotEmpty
    private String username;
    @NotEmpty
    private String repositoryName;

    public GithubRepoDTO() {
    }

    public GithubRepoDTO(String username, String repositoryName) {
        this.username = username;
        this.repositoryName = repositoryName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }
}
