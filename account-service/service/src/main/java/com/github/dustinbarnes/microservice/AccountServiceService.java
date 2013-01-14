package com.github.dustinbarnes.microservice;

import com.github.dustinbarnes.microservice.friends.client.FriendsServiceClient;
import com.github.dustinbarnes.microservice.health.FriendServiceHealthCheck;
import com.github.dustinbarnes.microservice.health.MoneyServiceHealthCheck;
import com.github.dustinbarnes.microservice.health.PhotoCacheHealthCheck;
import com.github.dustinbarnes.microservice.moneyservice.MoneyServiceClient;
import com.github.dustinbarnes.microservice.photocache.PhotoCacheClient;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class AccountServiceService extends Service<AccountServiceConfiguration>
{
    public static void main(String[] args) throws Exception
    {
        System.setProperty("dw.http.port", "8040");
        System.setProperty("dw.http.adminPort", "8041");

        new AccountServiceService().run(args);
    }

    @Override
    public void initialize(Bootstrap<AccountServiceConfiguration> bootstrap)
    {
        bootstrap.setName("account-service");
    }

    @Override
    public void run(AccountServiceConfiguration configuration, Environment environment) throws Exception
    {
        PhotoCacheClient photoCacheClient = new PhotoCacheClient(configuration.getPhotoCacheBasePathService(), configuration.getPhotoCacheBasePathAdmin());
        MoneyServiceClient moneyServiceClient = new MoneyServiceClient(configuration.getMoneyServiceBasePathService(), configuration.getMoneyServiceBasePathAdmin());
        FriendsServiceClient friendsServiceClient = new FriendsServiceClient(configuration.getFriendServiceBasePathService(), configuration.getFriendServiceBasePathAdmin());

        ExecutorService executorService = environment.managedExecutorService(
            "scatterGather-%d", 10, 50, 1, TimeUnit.SECONDS
        );

        WebServiceScatterGather scatterGather = new WebServiceScatterGather(executorService,
                moneyServiceClient, photoCacheClient, friendsServiceClient);

        environment.addHealthCheck(new MoneyServiceHealthCheck(moneyServiceClient));
        environment.addHealthCheck(new PhotoCacheHealthCheck(photoCacheClient));
        environment.addHealthCheck(new FriendServiceHealthCheck(friendsServiceClient));

        environment.addResource(new AccountServiceResource(configuration, scatterGather));
    }
}
