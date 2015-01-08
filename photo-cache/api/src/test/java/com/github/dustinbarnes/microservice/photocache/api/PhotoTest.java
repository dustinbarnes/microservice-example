package com.github.dustinbarnes.microservice.photocache.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class PhotoTest {
    private ObjectMapper JSON = new ObjectMapper();

    @Test
    public void PhotoSerializesCorrectly() throws Exception
    {
        Photo photo = new Photo("foo", "/var/tmp/abc123", "http://localhost:8080/abc123", "caption time");
        assertThat("Serialization works correctly",
                JSON.writeValueAsString(photo),
                is(equalTo(jsonFixture())));
    }

    @Test
    public void PhotoDeserializesCorrectly() throws Exception
    {
        Photo photo = new Photo("foo", "/var/tmp/abc123", "http://localhost:8080/abc123", "caption time");
        assertThat("Deserialization works correctly",
                JSON.readValue(jsonFixture(), Photo.class),
                is(equalTo(photo)));
    }

    private String jsonFixture() {
        try {
            return new String(Files.readAllBytes(Paths.get(getClass().getResource("/fixtures/photo.json").toURI())));
        } catch ( Exception e ) {
            e.printStackTrace();
            return "";
        }
    }
}
