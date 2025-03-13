package org.example.email_demo.controller;

import jakarta.validation.Valid;
import org.example.email_demo.dto.GithubRepoDTO;
import org.example.email_demo.model.GithubRepo;
import org.example.email_demo.service.GithubRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/github/api/")
public class GithubRepoController {

    private final GithubRepoService githubRepoService;

    @Autowired
    public GithubRepoController(GithubRepoService githubRepoService) {
        this.githubRepoService = githubRepoService;
    }

    @PostMapping("/repo")
    public ResponseEntity<GithubRepo> addRepo(@RequestBody @Valid GithubRepoDTO githubRepoDTO) {
        Optional<GithubRepo> repo = githubRepoService.addRepo(githubRepoDTO.getUsername(), githubRepoDTO.getRepositoryName());
        return repo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/repo/{id}")
    public ResponseEntity<Void> removeRepo(@PathVariable Long id) {
        githubRepoService.removeRepo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/repo/{id}")
    public ResponseEntity<GithubRepo> getRepo(@PathVariable Long id) {
        Optional<GithubRepo> repo = githubRepoService.getRepo(id);
        return repo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/repo")
    public ResponseEntity<List<GithubRepo>> getAllRepos() {
        List<GithubRepo> repos = githubRepoService.getAllRepos();
        return ResponseEntity.ok(repos);
    }

    @GetMapping("/repo/{username}/{repositoryName}")
    public ResponseEntity<GithubRepo> getRepo(@PathVariable String username, @PathVariable String repositoryName) {
        Optional<GithubRepo> repo = githubRepoService.getRepo(username, repositoryName);
        return repo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/repo/{username}/{repositoryName}/info")
    public ResponseEntity<String> getRepoInfo(@PathVariable String username, @PathVariable String repositoryName) {
        try {
            return ResponseEntity.ok(githubRepoService.getRepoInfo(username, repositoryName));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/repo/{id}/info")
    public ResponseEntity<String> getRepoInfo(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(githubRepoService.getRepoInfo(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


}
