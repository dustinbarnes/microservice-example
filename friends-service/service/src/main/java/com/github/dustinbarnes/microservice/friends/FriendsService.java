package com.github.dustinbarnes.microservice.friends;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class FriendsService extends Service<FriendsConfiguration>
{
    public static void main(String[] args) throws Exception
    {
        System.setProperty("dw.http.port", "8030");
        System.setProperty("dw.http.adminPort", "8031");

        new FriendsService().run(args);
    }

    @Override
    public void initialize(Bootstrap<FriendsConfiguration> bootstrap)
    {
        bootstrap.setName("friends-service");
    }

    @Override
    public void run(FriendsConfiguration configuration, Environment environment) throws Exception
    {
        environment.addResource(new FriendsResource(configuration));
    }

}
