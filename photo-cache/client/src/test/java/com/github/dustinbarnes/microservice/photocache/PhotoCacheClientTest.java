package com.github.dustinbarnes.microservice.photocache;

import com.github.dustinbarnes.microservice.photocache.api.Photo;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

public class PhotoCacheClientTest
{
    private static PhotoCacheService photoCacheService = null;
    private static PhotoCacheClient client = null;

    @BeforeClass
    public static void createEmbeddedService() throws Exception
    {
        photoCacheService = new PhotoCacheService();
        photoCacheService.startEmbeddedServer();
        if ( !photoCacheService.isEmbeddedServerRunning() )
        {
            throw new Exception("Unable to start service");
        }

        client = new PhotoCacheClient("http://localhost:8080", "http://localhost:8081");
    }

    @AfterClass
    public static void stopEmbeddedService() throws Exception
    {
        if ( client != null )
        {
            client.prepareForShutdown();
        }

        if ( photoCacheService.isEmbeddedServerRunning() )
        {
            photoCacheService.stopEmbeddedServer();
        }
    }

    @Test
    public void testFoo() throws IOException
    {
        client.ping();

        List<Photo> photos = client.getPhotos("abc123");
        assertThat(photos, notNullValue());
        assertThat(photos.size(), greaterThan(1));
        assertThat(photos.get(0).getUserid(), equalTo("abc123"));
    }
}
