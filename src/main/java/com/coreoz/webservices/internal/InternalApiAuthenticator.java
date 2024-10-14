package com.coreoz.webservices.internal;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import com.coreoz.plume.jersey.security.basic.BasicAuthenticator;
import com.coreoz.services.configuration.ConfigurationService;

@Singleton
public class InternalApiAuthenticator {
    private final BasicAuthenticator<String> basicAuthenticator;

    @Inject
    public InternalApiAuthenticator(ConfigurationService configurationService) {
        this.basicAuthenticator = BasicAuthenticator.fromSingleCredentials(
            configurationService.internalApiAuthUsername(),
            configurationService.internalApiAuthPassword(),
            "API plume-showcase"
        );
    }

    public BasicAuthenticator<String> get() {
        return this.basicAuthenticator;
    }
}
