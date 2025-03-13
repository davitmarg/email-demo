package org.example.email_demo.repository;

import org.example.email_demo.model.GithubRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GithubRepoRepository extends JpaRepository<GithubRepo, Long> {

    Optional<GithubRepo> findByUsernameAndRepositoryName(String username, String repositoryName);
}
