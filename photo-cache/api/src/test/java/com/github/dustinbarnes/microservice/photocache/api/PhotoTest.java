package com.github.dustinbarnes.microservice.photocache.api;

import org.junit.Test;

import static com.yammer.dropwizard.testing.JsonHelpers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PhotoTest
{
    @Test
    public void PhotoSerializesCorrectly() throws Exception
    {
        Photo photo = new Photo("foo", "/var/tmp/abc123", "http://localhost:8080/abc123", "caption time");
        assertThat("Serialization works correctly",
                asJson(photo),
                is(equalTo(jsonFixture("fixtures/photo.json"))));
    }

    @Test
    public void PhotoDeserializesCorrectly() throws Exception
    {
        Photo photo = new Photo("foo", "/var/tmp/abc123", "http://localhost:8080/abc123", "caption time");
        assertThat("Deserialization works correctly",
                fromJson(jsonFixture("fixtures/photo.json"), Photo.class),
                is(equalTo(photo)));
    }
}
