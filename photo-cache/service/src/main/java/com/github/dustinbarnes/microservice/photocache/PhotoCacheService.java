package com.github.dustinbarnes.microservice.photocache;

import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class PhotoCacheService extends EmbeddableService<PhotoCacheConfiguration>
{
    public static void main(String[] args) throws Exception
    {
        System.setProperty("dw.http.port", "8010");
        System.setProperty("dw.http.adminPort", "8011");

        new PhotoCacheService().run(args);
    }

    @Override
    public void init(Bootstrap<PhotoCacheConfiguration> bootstrap)
    {
        bootstrap.setName("photo-cache-service");
    }

    @Override
    public void run(PhotoCacheConfiguration configuration, Environment environment) throws Exception
    {
        environment.addHealthCheck(new PhotoCacheHealthCheck(configuration.getStoragePathRoot()));
        environment.addResource(new PhotoCacheResource(configuration));
    }
}
