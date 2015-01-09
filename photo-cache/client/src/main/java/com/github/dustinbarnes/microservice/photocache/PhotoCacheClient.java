package com.github.dustinbarnes.microservice.photocache;

import com.github.dustinbarnes.microservice.photocache.api.Photo;
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


public class PhotoCacheClient
{
    private String basePath;

    public PhotoCacheClient(String basePath)
    {
         this.basePath = basePath;
    }

    public List<Photo> getPhotos(String user) throws IOException {
        String url = basePath + "/" + user + "/photos";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        ParameterizedTypeReference<List<Photo>> type = new ParameterizedTypeReference<List<Photo>>(){};

        ResponseEntity<List<Photo>> response = new RestTemplate().exchange(url,
                HttpMethod.GET, new HttpEntity<Void>(headers), type);

        return response.getBody();
    }

    public Future<List<Photo>> getPhotosAsync(final String user, ExecutorService executorService) throws IOException {
        return executorService.submit(() -> getPhotos(user));
    }
}
