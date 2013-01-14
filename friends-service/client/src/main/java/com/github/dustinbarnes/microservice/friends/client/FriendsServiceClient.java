package com.github.dustinbarnes.microservice.friends.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dustinbarnes.microservice.friends.api.Friend;
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

public class FriendsServiceClient
{
    ObjectMapper om = new ObjectMapper();

    private HttpClient client;
    private String basePath;
    private String adminBasePath;

    public FriendsServiceClient(String basePath, String adminBasePath)
    {
        this.basePath = basePath;
        this.adminBasePath = adminBasePath;

        client = new HttpClientBuilder().build();
    }

    public List<Friend> getFriends(String user) throws IOException
    {
        HttpGet get = new HttpGet(basePath + "/" + user + "/friends");
        get.setHeader("Accept", MediaType.APPLICATION_JSON);
        HttpResponse response = client.execute(get);
        return om.readValue(response.getEntity().getContent(), new TypeReference<List<Friend>>(){});
    }

    public Future<List<Friend>> getFriendsAsync(final String user, ExecutorService executorService) throws IOException
    {
        return executorService.submit(new Callable<List<Friend>>()
        {
            @Override
            public List<Friend> call() throws Exception
            {
                return getFriends(user);
            }
        });
    }

    public void ping() throws IOException
    {
        HttpGet get = new HttpGet(adminBasePath + "/ping");
        HttpResponse response = client.execute(get);
        if ( response.getStatusLine().getStatusCode() != HttpStatus.SC_OK )
        {
            throw new RuntimeException("Expected 200 from ping, got " + response.getStatusLine().getStatusCode());
        }
    }
}
