package com.github.dustinbarnes.microservice.photocache;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dustinbarnes.microservice.photocache.api.Photo;
import com.yammer.dropwizard.client.HttpClientBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class PhotoCacheClient
{
    ObjectMapper om = new ObjectMapper();

    private HttpClient client;
    private String basePath;
    private String adminBasePath;

    public PhotoCacheClient(String basePath, String adminBasePath)
    {
        this.basePath = basePath;
        this.adminBasePath = adminBasePath;

        client = new HttpClientBuilder().build();
    }

    public List<Photo> getPhotos(String user) throws IOException
    {
        HttpGet get = new HttpGet(basePath + "/" + user + "/photos");
        get.setHeader("Accept", MediaType.APPLICATION_JSON);
        HttpResponse response = client.execute(get);
        return om.readValue(response.getEntity().getContent(), new TypeReference<List<Photo>>(){});
    }

    public Future<List<Photo>> getPhotosAsync(final String user, ExecutorService executorService) throws IOException
    {
        return executorService.submit(new Callable<List<Photo>>() {
            @Override
            public List<Photo> call() throws Exception
            {
                return getPhotos(user);
            }
        });
    }

    public void ping() throws IOException
    {
        HttpGet get = new HttpGet(adminBasePath + "/ping");
        HttpResponse response = client.execute(get);
        if ( response.getStatusLine().getStatusCode() != HttpStatus.SC_OK )
        {
            throw new RuntimeException("Expected 200 from ping, got " + response.getStatusLine().getStatusCode() );
        }
    }
}
