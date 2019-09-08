package com.mycompany.restclient;

import java.time.Duration;
import java.util.Collections;
import java.util.Set;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {

    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        // set connection and read timeouts
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(500))
                .setReadTimeout(Duration.ofSeconds(500))
                .build();
    }

    public Object get(String uri, Class<?> className) {
        return this.restTemplate.getForObject(uri, className);
    }

    public Object post(String uri, Object object) {
        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Class className = object.getClass();

        // build the request
        HttpEntity<?> entity = new HttpEntity<>(object, headers);

        // send POST request
        ResponseEntity<?> response = this.restTemplate.postForEntity(uri, entity, className);

        // check response status code
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public Object put(String uri, Object object, int id) {
        
        uri += "/{id}";
        
        // create headers
        HttpHeaders headers = new HttpHeaders();
        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // build the request
        HttpEntity<?> entity = new HttpEntity<>(object, headers);
        
        Class className = object.getClass();

        // send PUT request to update post with `id` 10
        ResponseEntity<?> response = this.restTemplate.exchange(uri, HttpMethod.PUT, entity, className, id);

        // check response status code
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public void deletePost() {
        String url = "https://jsonplaceholder.typicode.com/posts/{id}";

        // send DELETE request to delete post with `id` 10
        this.restTemplate.delete(url, 10);
    }

    public HttpHeaders retrieveHeaders(String uri) {
        return this.restTemplate.headForHeaders(uri);
    }

    public Set<HttpMethod> allowedOperations(String uri) {
        return this.restTemplate.optionsForAllow(uri);
    }

    Object get(String uri, Class className, int id) {
        uri += "/{id}";
        return this.restTemplate.getForObject(uri, className, id);
    }

    void delete(String uri, int id) {
        uri += "/{id}";
        this.restTemplate.delete(uri, id);
    }

    String getAsJSON(String uri) {
        return this.restTemplate.getForObject(uri, String.class);
    }
}
