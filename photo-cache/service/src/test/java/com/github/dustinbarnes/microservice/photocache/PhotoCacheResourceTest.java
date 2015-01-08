package com.github.dustinbarnes.microservice.photocache;

import com.github.dustinbarnes.microservice.photocache.api.Photo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PhotoCacheApplication.class)
@WebIntegrationTest({"server.port=0", "management.port=0"})
public class PhotoCacheResourceTest {
    private final Photo photo = new Photo("test", "/tmp/abc123", "/abc123", "caption");

    @Value("${local.server.port}")
    int port;

    RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testExpecting404OnPost() {
        String url = "http://localhost:" + port + "/404foo/photos";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Photo> entity = new HttpEntity<>(photo, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,
                entity, String.class);

        assertThat("Posting with a 404 prefix for user id generates 404",
                response.getStatusCode(),
                equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    public void testExpecting500OnPost() {
        String url = "http://localhost:" + port + "/500foo/photos";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Photo> entity = new HttpEntity<>(photo, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,
                entity, String.class);

        assertThat("Posting with a 500 prefix for user id generates server error",
                response.getStatusCode(),
                equalTo(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void testExpecting201OnPost() {
        String url = "http://localhost:" + port + "/doggy/photos";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Photo> entity = new HttpEntity<>(photo, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,
                entity, String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.CREATED));
    }

    @Test
    public void testGettingPhotos() {
        String url = "http://localhost:" + port + "/doggy/photos";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        ParameterizedTypeReference<List<Photo>> type = new ParameterizedTypeReference<List<Photo>>(){};

        ResponseEntity<List<Photo>> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Void>(headers), type);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        List<Photo> photos = response.getBody();

        assertThat("We got some photos",
                photos,
                hasSize(both(greaterThanOrEqualTo(1)).and(lessThanOrEqualTo(20))));
    }
}
