package com.github.dustinbarnes.microservice.friends;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.RandomBasedGenerator;
import com.github.dustinbarnes.microservice.friends.api.Friend;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RestController
@RequestMapping(value="/{user}/friends", produces=MediaType.APPLICATION_JSON_VALUE)
public class FriendsEndpoint {
    private static final Random random = new Random();
    private static final RandomBasedGenerator generator = Generators.randomBasedGenerator(random);

    @RequestMapping(method=RequestMethod.GET)
    public List<Friend> getFriends(@PathVariable @NotEmpty String user) {
        int numFriends = getNumberInRange(5, 50);
        List<Friend> friends = new ArrayList<>();

        for ( int i = 0; i < numFriends; i++ ) {
            friends.add(new Friend("friend-" + getNumberInRange(10000, 20000), "http://localhost:8080/foobar"));
        }

        // Act like it's doing some real calculations
        try {
            Thread.sleep((long)getNumberInRange(2, 50));
        } catch ( InterruptedException e ) {
            // ignore
        }

        return friends;
    }

    private int getNumberInRange(int min, int max)
    {
        return min + (random.nextInt(max - min + 1));
    }
}
