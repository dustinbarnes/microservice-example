package com.github.dustinbarnes.microservice.photocache;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dustinbarnes.microservice.photocache.api.Photo;
import com.yammer.dropwizard.client.HttpClientBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

public class PhotoCacheClient
{
    ObjectMapper om = new ObjectMapper();

    private HttpClient client;
    private String basePath;

    public PhotoCacheClient(String basePath)
    {
        this.basePath = basePath;
        client = new HttpClientBuilder().build();
    }

    public List<Photo> getPhotos(String user) throws IOException
    {
        HttpGet get = new HttpGet(basePath + "/" + user + "/photos");
        get.setHeader("Accept", MediaType.APPLICATION_JSON);
        HttpResponse response = client.execute(get);
        return om.readValue(response.getEntity().getContent(), new TypeReference<List<Photo>>(){});
    }
}
