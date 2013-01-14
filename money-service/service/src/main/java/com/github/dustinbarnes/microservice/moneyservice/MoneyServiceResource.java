package com.github.dustinbarnes.microservice.moneyservice;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.RandomBasedGenerator;
import com.github.dustinbarnes.microservice.money.api.BalanceStatement;
import com.github.dustinbarnes.microservice.money.api.MoneyTransaction;
import com.yammer.metrics.annotation.Timed;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Random;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/{user}/money")
public class MoneyServiceResource
{
    private MoneyServiceConfiguration configuration;
    private static final Random random = new Random();

    public MoneyServiceResource(MoneyServiceConfiguration configuration)
    {
        this.configuration = configuration;
    }

    @POST
    @Timed
    public BalanceStatement addTransaction(@PathParam("user") @NotEmpty String user, @Valid MoneyTransaction transaction)
    {
        if ( user.startsWith("500") )
        {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
        else if ( user.startsWith("404") )
        {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        else
        {
            return new BalanceStatement(user, transaction.getAmount(), 0.00f);
        }
    }

    @GET
    @Timed
    public BalanceStatement getBalance(@PathParam("user") @NotEmpty String user)
    {

        // Act like it's doing some real calculations
        try
        {
            Thread.sleep((long) getNumberInRange(2, 50));
        }
        catch ( InterruptedException e )
        {
            // ignore
        }

        return new BalanceStatement(user, 100.00f, 0.00f);
    }

    private int getNumberInRange(int min, int max)
    {
        return min + (random.nextInt(max - min + 1));
    }
}
