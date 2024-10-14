package com.coreoz.services.configuration;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import com.typesafe.config.Config;

@Singleton
public class ConfigurationService {
	private final Config config;

	@Inject
	public ConfigurationService(Config config) {
		this.config = config;
	}

	public String hello() {
		return config.getString("hello");
	}

    public String internalApiAuthUsername() {
        return config.getString("internal-api.auth-username");
    }

    public String internalApiAuthPassword() {
        return config.getString("internal-api.auth-password");
    }
}
