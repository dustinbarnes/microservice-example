package com.github.dustinbarnes.microservice.photocache;

import com.github.dustinbarnes.microservice.photocache.api.Photo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PhotoCacheApplication.class)
@WebIntegrationTest({"server.port=0", "management.port=0"})
public class PhotoCacheClientTest {
    @Value("${local.server.port}")
    int port;

    PhotoCacheClient client;

    @Before
    public void configureClient() {
        client = new PhotoCacheClient("http://localhost:" + port);
    }

    @Test
    public void testPhotos() throws IOException {
        List<Photo> photos = client.getPhotos("abc123");
        assertThat(photos, notNullValue());
        assertThat(photos.size(), greaterThan(1));
        assertThat(photos.get(0).getUserid(), equalTo("abc123"));
    }
}
