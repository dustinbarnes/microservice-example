package com.github.dustinbarnes.microservice.photocache;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Configuration;

public abstract class EmbeddableService<T extends Configuration> extends Service<T>
{
    private final EmbeddedServerCommand<T> embeddedServerCommand = new EmbeddedServerCommand<T>(this);

    public void startEmbeddedServer(String configFileName) throws Exception
    {
        run(new String[] {"embedded-server", configFileName});
    }

    public void startEmbeddedServer() throws Exception
    {
        run(new String[] {"embedded-server"});
    }

    public void stopEmbeddedServer() throws Exception
    {
        embeddedServerCommand.stop();
    }

    public boolean isEmbeddedServerRunning()
    {
        return embeddedServerCommand.isRunning();
    }

    @Override
    public final void initialize(Bootstrap<T> bootstrap)
    {
        bootstrap.addCommand(embeddedServerCommand);
        init(bootstrap);
    }

    abstract void init(Bootstrap<T> bootstrap);
}

