package com.github.dustinbarnes.microservice.moneyservice;

import com.yammer.metrics.core.HealthCheck;

public class MoneyServiceHealthCheck extends HealthCheck
{
    protected MoneyServiceHealthCheck()
    {
        super("money-service");
    }

    @Override
    protected Result check() throws Exception
    {
        return Result.healthy();
    }
}
