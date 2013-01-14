package com.github.dustinbarnes.microservice.health;

import com.github.dustinbarnes.microservice.friends.client.FriendsServiceClient;
import com.github.dustinbarnes.microservice.moneyservice.MoneyServiceClient;
import com.yammer.metrics.core.HealthCheck;

public class FriendServiceHealthCheck extends HealthCheck
{
    private final FriendsServiceClient client;

    public FriendServiceHealthCheck(FriendsServiceClient client)
    {
        super("friend-service");
        this.client = client;
    }

    @Override
    protected Result check() throws Exception
    {
        client.ping();
        return Result.healthy();
    }
}
