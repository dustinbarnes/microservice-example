package com.github.dustinbarnes.microservice.health;

import com.github.dustinbarnes.microservice.photocache.PhotoCacheClient;
import com.yammer.metrics.core.HealthCheck;

public class PhotoCacheHealthCheck extends HealthCheck
{
    private PhotoCacheClient client;

    public PhotoCacheHealthCheck(PhotoCacheClient client)
    {
        super("photo-cache");
        this.client = client;
    }

    @Override
    protected Result check() throws Exception
    {
        client.ping();
        return Result.healthy();
    }
}
