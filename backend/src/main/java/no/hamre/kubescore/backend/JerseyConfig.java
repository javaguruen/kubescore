package no.hamre.kubescore.backend;

import no.hamre.kubescore.backend.controller.ScrambleController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig
{
    public JerseyConfig()
    {
        register(ScrambleController.class);
    }
}