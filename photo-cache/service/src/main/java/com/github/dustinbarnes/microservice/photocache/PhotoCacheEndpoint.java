package com.github.dustinbarnes.microservice.photocache;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.RandomBasedGenerator;
import com.github.dustinbarnes.microservice.photocache.api.Photo;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@RestController
@RequestMapping(value="/{user}/photos", produces=MediaType.APPLICATION_JSON_VALUE)
public class PhotoCacheEndpoint {
    private static final Random random = new Random();
    private static final RandomBasedGenerator generator = Generators.randomBasedGenerator(random);

    @Value("${storagePathRoot}")
    private String storagePathRoot;

    @Value("${webPathRoot}")
    private String webPathRoot;

    @RequestMapping(method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addPhoto(@PathVariable @NotEmpty String user, @Valid Photo photo)
    {
        if ( user.startsWith("500") ) {
            return new ResponseEntity<>("You asked for an error...", HttpStatus.INTERNAL_SERVER_ERROR);
        } else if ( user.startsWith("404") ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    @RequestMapping(method=RequestMethod.GET)
    public List<Photo> getPhotos(@PathVariable @NotEmpty String user)
    {
        int numPhotos = getNumberInRange(1, 20);
        List<Photo> photos = new ArrayList<>();

        for ( int i = 0; i < numPhotos; i++ ) {
            String photoid = generator.generate().toString();
            photos.add(new Photo(user,
                    storagePathRoot + photoid,
                    webPathRoot + photoid,
                    "This is a photo!"));
        }

        // Act like it's doing some real calculations
        try {
            Thread.sleep((long) getNumberInRange(2, 50));
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }

        return photos;
    }

    private int getNumberInRange(int min, int max)
    {
        return min + (random.nextInt(max - min + 1));
    }
}
