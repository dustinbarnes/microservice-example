package com.github.dustinbarnes.microservice;

import com.github.dustinbarnes.microservice.friends.api.Friend;
import com.github.dustinbarnes.microservice.friends.client.FriendsServiceClient;
import com.github.dustinbarnes.microservice.money.api.BalanceStatement;
import com.github.dustinbarnes.microservice.moneyservice.MoneyServiceClient;
import com.github.dustinbarnes.microservice.photocache.PhotoCacheClient;
import com.github.dustinbarnes.microservice.photocache.api.Photo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class WebServiceScatterGather
{
    private final MoneyServiceClient moneyServiceClient;
    private final PhotoCacheClient photoCacheClient;
    private final ExecutorService executorService;
    private final FriendsServiceClient friendsServiceClient;

    public WebServiceScatterGather(ExecutorService executorService, MoneyServiceClient moneyServiceClient, PhotoCacheClient photoCacheClient, FriendsServiceClient friendsServiceClient)
    {
        this.executorService = executorService;
        this.moneyServiceClient = moneyServiceClient;
        this.photoCacheClient = photoCacheClient;
        this.friendsServiceClient = friendsServiceClient;
    }

    public Map<String, Object> getAccount(String user)
    {
        Map<String, Object> properties = new HashMap<>();

        try
        {
            Future<BalanceStatement> balanceStatementFuture = moneyServiceClient.getBalanceAsync(user, executorService);
            Future<List<Photo>> photoListFuture = photoCacheClient.getPhotosAsync(user, executorService);
            Future<List<Friend>> friendListFuture = friendsServiceClient.getFriendsAsync(user, executorService);

            properties.put("balance", balanceStatementFuture.get());
            properties.put("photos", photoListFuture.get());
            properties.put("friends", friendListFuture.get());
        }
        catch ( InterruptedException | ExecutionException | IOException e )
        {
            properties.put("error", e);
        }

        return properties;
    }

    public Map<String, Object> getAccountSync(String user)
    {
        Map<String, Object> properties = new HashMap<>();

        try
        {
            properties.put("balance", moneyServiceClient.getBalance(user));
            properties.put("photos", photoCacheClient.getPhotos(user));
            properties.put("friends", friendsServiceClient.getFriends(user));
        }
        catch ( IOException e )
        {
            properties.put("error", e);
        }

        return properties;
    }
}
