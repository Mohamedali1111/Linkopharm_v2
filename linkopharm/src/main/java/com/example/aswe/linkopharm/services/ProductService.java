package com.example.aswe.linkopharm.services;

import com.example.aswe.linkopharm.models.products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ProductService {

    private String baseUrl = "http://localhost:8085"; 
    private RestTemplate restTemplate;

    public ProductService() {
        this.restTemplate = new RestTemplate(); 
    }

    public List<products> findAll() {
        String url = baseUrl + "/products";
        return this.restTemplate.exchange(
                url, 
                HttpMethod.GET, 
                null,
                new ParameterizedTypeReference<List<products>>() {
                }).getBody();
    }

    public products findById(Integer id) {
        String url = baseUrl + "/products/" + id;
        return this.restTemplate.getForObject(url, products.class);
    }

    public void save(products product) {
        String url = baseUrl + "/products/save";
        this.restTemplate.postForObject(url, product, products.class);
    }

    public void deleteById(Integer id) {
        String url = baseUrl + "/products/delete/" + id;
        this.restTemplate.delete(url);
    }

}
