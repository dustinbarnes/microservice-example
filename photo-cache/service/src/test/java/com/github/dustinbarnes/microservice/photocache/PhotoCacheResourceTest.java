package com.github.dustinbarnes.microservice.photocache;

import com.github.dustinbarnes.microservice.photocache.api.Photo;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.yammer.dropwizard.testing.ResourceTest;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PhotoCacheResourceTest extends ResourceTest
{
    private final Photo photo = new Photo("test", "/tmp/abc123", "/abc123", "caption");

    @Override
    protected void setUpResources()
    {
        addResource(new PhotoCacheResource(new PhotoCacheConfiguration()));
    }

    @Test
    public void testExpecting404OnPost()
    {
        ClientResponse response = client()
                .resource("/404person/photos")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, photo);

        assertThat("Posting with a 404 prefix for user id generates 404",
                response.getClientResponseStatus(),
                equalTo(ClientResponse.Status.NOT_FOUND));
    }

    @Test
    public void testExpecting500OnPost()
    {
        ClientResponse response = client()
                .resource("/500foo/photos")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, photo);

        assertThat("Posting with a 500 prefix for user id generates server error",
                response.getClientResponseStatus(),
                equalTo(ClientResponse.Status.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void testExpecting201OnPost()
    {
        ClientResponse response = client()
                .resource("/doggy/photos")
                .type(MediaType.APPLICATION_JSON_TYPE)
                .post(ClientResponse.class, photo);

        assertThat("Posting valid data gets us the proper code",
                response.getClientResponseStatus(),
                equalTo(ClientResponse.Status.CREATED));
    }

    @Test
    public void testGettingPhotos()
    {
        List<Photo> photos = client()
                .resource("/doggy/photos")
                .get(new GenericType<List<Photo>>(){});

        assertThat("We got some photos",
                photos,
                hasSize(both(greaterThanOrEqualTo(1)).and(lessThanOrEqualTo(20))));
    }
}
