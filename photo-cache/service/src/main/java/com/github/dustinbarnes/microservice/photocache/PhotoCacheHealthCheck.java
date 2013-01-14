package com.github.dustinbarnes.microservice.photocache;

import com.yammer.metrics.core.HealthCheck;

import java.io.File;

public class PhotoCacheHealthCheck extends HealthCheck
{
    private String storagePath;

    public PhotoCacheHealthCheck(String storagePath)
    {
        super("photo");
        this.storagePath = storagePath;
    }

    @Override
    protected Result check() throws Exception
    {
        File f = new File(storagePath);
        if ( f.isDirectory() && f.canWrite() )
        {
            return Result.healthy();
        }
        else
        {
            return Result.unhealthy("Specified storage path [" + storagePath + "] is not a directory, or is not writeable");
        }
    }
}
