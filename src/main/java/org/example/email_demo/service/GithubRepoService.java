package org.example.email_demo.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.example.email_demo.model.GithubRepo;
import org.example.email_demo.model.RepoActivity;
import org.example.email_demo.repository.GithubRepoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Comparator;
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

    public List<RepoActivity> getRecentActivities(String username, String repositoryName) {
        List<RepoActivity> activityList = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();

        try {
            String commitsUrl = "https://api.github.com/repos/" + username + "/" + repositoryName + "/commits";
            HttpRequest commitsRequest = HttpRequest.newBuilder()
                    .uri(URI.create(commitsUrl))
                    .header("Accept", "application/vnd.github.v3+json")
                    .build();

            HttpResponse<String> commitsResponse = client.send(commitsRequest, HttpResponse.BodyHandlers.ofString());

            if (commitsResponse.statusCode() == 200) {
                JsonArray commitsArray = JsonParser.parseString(commitsResponse.body()).getAsJsonArray();
                for (int i = 0; i < commitsArray.size(); i++) {
                    var commitObj = commitsArray.get(i).getAsJsonObject();
                    String sha = commitObj.get("sha").getAsString();
                    String message = commitObj.getAsJsonObject("commit").get("message").getAsString();
                    String date = commitObj.getAsJsonObject("commit").getAsJsonObject("author").get("date").getAsString();

                    RepoActivity activity = new RepoActivity(sha, message, date);

                    activity.setUsername(username);
                    activity.setRepositoryName(repositoryName);
                    activityList.add(activity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String pullsUrl = "https://api.github.com/repos/" + username + "/" + repositoryName + "/pulls?state=all";
            HttpRequest pullsRequest = HttpRequest.newBuilder()
                    .uri(URI.create(pullsUrl))
                    .header("Accept", "application/vnd.github.v3+json")
                    .build();
            HttpResponse<String> pullsResponse = client.send(pullsRequest, HttpResponse.BodyHandlers.ofString());
            if (pullsResponse.statusCode() == 200) {
                JsonArray pullsArray = JsonParser.parseString(pullsResponse.body()).getAsJsonArray();

                for (int i = 0; i < pullsArray.size(); i++) {
                    var pullObj = pullsArray.get(i).getAsJsonObject();
                    int number = pullObj.get("number").getAsInt();
                    String title = pullObj.get("title").getAsString();
                    String state = pullObj.get("state").getAsString();
                    String createdAt = pullObj.get("created_at").getAsString();
                    RepoActivity activity = new RepoActivity(String.valueOf(number), title, createdAt, state);
                    activity.setUsername(username);
                    activity.setRepositoryName(repositoryName);
                    activityList.add(activity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        activityList.sort(Comparator.comparing(RepoActivity::getTimestamp));

        return activityList;
    }

    public List<RepoActivity> getRecentActivities(Long id) {
        Optional<GithubRepo> repo = getRepo(id);
        if (repo.isPresent()) {
            GithubRepo githubRepo = repo.get();
            return getRecentActivities(githubRepo.getUsername(), githubRepo.getRepositoryName());
        } else {
            return new ArrayList<>();
        }
    }

}
