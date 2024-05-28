package com.example.aswe.linkopharm.services;

import com.example.aswe.linkopharm.models.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {

    private String baseUrl = "http://localhost:8088"; 
    private RestTemplate restTemplate;

    public UserService() {
        this.restTemplate = new RestTemplate(); 
    }

    public List<User> findAll() {
        String url = baseUrl + "/users";
        return this.restTemplate.exchange(
                url, 
                HttpMethod.GET, 
                null,
                new ParameterizedTypeReference<List<User>>() {
                }).getBody();
    }

    public User findById(Integer id) {
        String url = baseUrl + "/users/" + id;
        return this.restTemplate.getForObject(url, User.class);
    }

    public void save(User user) {
        String url = baseUrl + "/users/save";
        this.restTemplate.postForObject(url, user, User.class);
    }

    public void delete(Integer id) {
        String url = baseUrl + "/users/delete/" + id;
        this.restTemplate.delete(url);
    }

    public User findByEmail(String email) {
        String url = baseUrl + "/users/email/" + email;
        return this.restTemplate.getForObject(url, User.class);
    }
}
