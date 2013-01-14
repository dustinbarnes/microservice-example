package com.github.dustinbarnes.microservice;


import com.github.dustinbarnes.microservice.moneyservice.MoneyServiceClient;
import com.github.dustinbarnes.microservice.photocache.PhotoCacheClient;
import com.yammer.metrics.annotation.Timed;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/{user}/account")
public class AccountServiceResource
{
    private final AccountServiceConfiguration config;
    private final WebServiceScatterGather scatterGather;

    public AccountServiceResource(AccountServiceConfiguration configuration, WebServiceScatterGather scatterGather)
    {
        this.config = configuration;
        this.scatterGather = scatterGather;
    }

    @GET
    @Path("/async")
    @Timed
    public Response getAccountAsync(@PathParam("user") @NotEmpty String user)
    {
        Map<String, Object> response = scatterGather.getAccount(user);
        response.put("user", user);
        response.put("password", "password123");

        return Response.ok().entity(response).build();
    }

    @GET
    @Path("/sync")
    @Timed
    public Response getAccountSync(@PathParam("user") @NotEmpty String user)
    {
        Map<String, Object> response = scatterGather.getAccountSync(user);
        response.put("user", user);
        response.put("password", "password123");

        return Response.ok().entity(response).build();
    }
}
