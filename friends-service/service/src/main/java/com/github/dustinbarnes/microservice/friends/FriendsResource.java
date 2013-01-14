package com.github.dustinbarnes.microservice.friends;

import com.github.dustinbarnes.microservice.friends.api.Friend;
import com.yammer.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Path("/{user}/friends")
public class FriendsResource
{
    private final FriendsConfiguration configuration;
    private static final Random random = new Random();

    public FriendsResource(FriendsConfiguration configuration)
    {
        this.configuration = configuration;
    }

    @GET
    @Timed
    public List<Friend> getFriends()
    {
        int numFriends = getNumberInRange(5, 50);
        List<Friend> friends = new ArrayList<>();

        for ( int i = 0; i < numFriends; i++ )
        {
            friends.add(new Friend("friend-" + getNumberInRange(10000, 20000), "http://localhost:8080/foobar"));
        }

        // Act like it's doing some real calculations
        try
        {
            Thread.sleep((long)getNumberInRange(2, 50));
        }
        catch ( InterruptedException e )
        {
            // ignore
        }

        return friends;
    }

    private int getNumberInRange(int min, int max)
    {
        return min + (random.nextInt(max - min + 1));
    }
}
