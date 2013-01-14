package com.github.dustinbarnes.microservice.photocache;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

public class PhotoCacheConfiguration extends Configuration
{
    @JsonProperty
    private String storagePathRoot = "/tmp";

    @JsonProperty
    private String webPathRoot = "http://localhost:8080/images/";

    public String getStoragePathRoot()
    {
        return storagePathRoot;
    }

    public String getWebPathRoot()
    {
        return webPathRoot;
    }
}
