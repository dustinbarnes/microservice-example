package com.github.dustinbarnes.microservice.photocache;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.RandomBasedGenerator;
import com.github.dustinbarnes.microservice.photocache.api.Photo;
import com.yammer.metrics.annotation.Timed;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/{user}/photos")
public class PhotoCacheResource
{
    private PhotoCacheConfiguration configuration;
    private static final Random random = new Random();
    private static final RandomBasedGenerator generator = Generators.randomBasedGenerator(random);

    public PhotoCacheResource(PhotoCacheConfiguration configuration)
    {
        this.configuration = configuration;
    }

    @POST
    @Timed
    public Response addPhoto(@PathParam("user") @NotEmpty String user, @Valid Photo photo)
    {
        if ( user.startsWith("500") )
        {
            return Response.serverError().entity("You asked for an error...").build();
        }
        else if ( user.startsWith("404") )
        {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else
        {
            return Response.status(Response.Status.CREATED).build();
        }
    }

    @GET
    @Timed
    public List<Photo> getPhotos(@PathParam("user") @NotEmpty String user)
    {
        int numPhotos = getNumberInRange(1, 20);
        List<Photo> photos = new ArrayList<>();

        for ( int i = 0; i < numPhotos; i++ )
        {
            String photoid = generator.generate().toString();
            photos.add(new Photo(user,
                    configuration.getStoragePathRoot() + photoid,
                    configuration.getWebPathRoot() + photoid,
                    "This is a photo!"));
        }

        // Act like it's doing some real calculations
        try
        {
            Thread.sleep((long) getNumberInRange(2, 50));
        }
        catch ( InterruptedException e )
        {
            // ignore
        }

        return photos;
    }

    private int getNumberInRange(int min, int max)
    {
        return min + (random.nextInt(max - min + 1));
    }
}
