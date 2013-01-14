package com.github.dustinbarnes.microservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

public class AccountServiceConfiguration extends Configuration
{
    @JsonProperty
    private String photoCacheBasePathService = "http://localhost:8010";

    @JsonProperty
    private String photoCacheBasePathAdmin = "http://localhost:8011";

    @JsonProperty
    private String moneyServiceBasePathService = "http://localhost:8020";

    @JsonProperty
    private String moneyServiceBasePathAdmin = "http://localhost:8021";

    @JsonProperty
    private String friendServiceBasePathService = "http://localhost:8030";

    @JsonProperty
    private String friendServiceBasePathAdmin = "http://localhost:8031";

    public String getPhotoCacheBasePathService()
    {
        return photoCacheBasePathService;
    }

    public String getPhotoCacheBasePathAdmin()
    {
        return photoCacheBasePathAdmin;
    }

    public String getMoneyServiceBasePathService()
    {
        return moneyServiceBasePathService;
    }

    public String getMoneyServiceBasePathAdmin()
    {
        return moneyServiceBasePathAdmin;
    }

    public String getFriendServiceBasePathService()
    {
        return friendServiceBasePathService;
    }

    public String getFriendServiceBasePathAdmin()
    {
        return friendServiceBasePathAdmin;
    }
}
