package org.example.email_demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class GithubRepo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String repositoryName;

    public GithubRepo() {

    }

    public GithubRepo(String username, String repositoryName) {
        this.username = username;
        this.repositoryName = repositoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "GithubRepo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", repositoryName='" + repositoryName + '\'' +
                '}';
    }

}
