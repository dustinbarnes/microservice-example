package com.github.dustinbarnes.microservice.friends.client;

import com.github.dustinbarnes.microservice.friends.api.Friend;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class FriendsServiceClient {
    private String basePath;

    public FriendsServiceClient(String basePath) {
        this.basePath = basePath;
    }

    public List<Friend> getFriends(String user) throws IOException {
            String url = basePath + "/" + user + "/friends";

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            ParameterizedTypeReference<List<Friend>> type = new ParameterizedTypeReference<List<Friend>>(){};

            ResponseEntity<List<Friend>> response = new RestTemplate().exchange(url,
                    HttpMethod.GET, new HttpEntity<Void>(headers), type);

            return response.getBody();
        }

        public Future<List<Friend>> getFriendsAsync(final String user, ExecutorService executorService) throws IOException        {
            return executorService.submit(() -> getFriends(user));
        }
}
