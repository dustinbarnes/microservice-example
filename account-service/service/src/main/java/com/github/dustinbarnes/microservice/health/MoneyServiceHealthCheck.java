package com.github.dustinbarnes.microservice.health;

import com.github.dustinbarnes.microservice.moneyservice.MoneyServiceClient;
import com.yammer.metrics.core.HealthCheck;

public class MoneyServiceHealthCheck extends HealthCheck
{
    private final MoneyServiceClient client;

    public MoneyServiceHealthCheck(MoneyServiceClient client)
    {
        super("money-service");
        this.client = client;
    }

    @Override
    protected Result check() throws Exception
    {
        client.ping();
        return Result.healthy();
    }
}
