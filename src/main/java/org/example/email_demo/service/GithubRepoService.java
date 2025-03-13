package org.example.email_demo.service;

import com.google.gson.Gson;
import org.example.email_demo.model.GithubRepo;
import org.example.email_demo.repository.GithubRepoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@Service
public class GithubRepoService {

    private final GithubRepoRepository githubRepoRepository;

    @Autowired
    public GithubRepoService(GithubRepoRepository githubRepoRepository) {
        this.githubRepoRepository = githubRepoRepository;
    }

    public Optional<GithubRepo> addRepo(String username, String repositoryName) {
        return Optional.of(githubRepoRepository.save(new GithubRepo(username, repositoryName)));
    }

    public void removeRepo(Long id) {
        githubRepoRepository.deleteById(id);
    }

    public Optional<GithubRepo> getRepo(Long id) {
        return githubRepoRepository.findById(id);
    }

    public Optional<GithubRepo> getRepo(String username, String repositoryName) {
        return githubRepoRepository.findByUsernameAndRepositoryName(username, repositoryName);
    }

    public List<GithubRepo> getAllRepos() {
        return githubRepoRepository.findAll();
    }


    public String getRepoInfo(String username, String repositoryName) throws Exception {

        String apiUrl = "https://api.github.com/repos/" + username + "/" + repositoryName;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Accept", "application/vnd.github.v3+json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new Exception("Failed to fetch repository info. HTTP Code: " + response.statusCode());
        }
    }

    public String getRepoInfo(Long id) throws Exception {
        Optional<GithubRepo> repo = getRepo(id);
        if (repo.isPresent()) {
            GithubRepo githubRepo = repo.get();
            return getRepoInfo(githubRepo.getUsername(), githubRepo.getRepositoryName());
        } else {
            throw new Exception("Repository not found");
        }
    }


}
