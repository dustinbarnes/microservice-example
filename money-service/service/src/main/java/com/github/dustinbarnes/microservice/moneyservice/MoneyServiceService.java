package com.github.dustinbarnes.microservice.moneyservice;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class MoneyServiceService extends Service<MoneyServiceConfiguration>
{
    public static void main(String[] args) throws Exception
    {
        System.setProperty("dw.http.port", "8020");
        System.setProperty("dw.http.adminPort", "8021");

        new MoneyServiceService().run(args);
    }

    @Override
    public void initialize(Bootstrap<MoneyServiceConfiguration> bootstrap)
    {
        bootstrap.setName("money-service");
    }

    @Override
    public void run(MoneyServiceConfiguration configuration, Environment environment) throws Exception
    {
        environment.addHealthCheck(new MoneyServiceHealthCheck());
        environment.addResource(new MoneyServiceResource(configuration));
    }
}
