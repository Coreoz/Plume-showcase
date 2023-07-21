package com.coreoz.webservices.internal;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.coreoz.plume.jersey.security.basic.BasicAuthenticator;
import com.coreoz.plume.jersey.security.permission.PublicApi;
import com.coreoz.services.configuration.ConfigurationService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.integration.api.OpenApiContext;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import lombok.SneakyThrows;

@Path("/swagger")
@Singleton
@PublicApi
public class SwaggerWs {

	private final String swaggerDefinition;
	private final BasicAuthenticator<String> basicAuthenticator;

	@Inject
	public SwaggerWs(ConfigurationService configurationService) throws OpenApiConfigurationException {
		SwaggerConfiguration openApiConfig = new SwaggerConfiguration()
			.resourcePackages(Set.of("com.coreoz.webservices.api"))
			.sortOutput(true)
			.openAPI(new OpenAPI().servers(List.of(
				new Server()
					.url("/api")
					.description("API plume-showcase")
			)));

		OpenApiContext context = new JaxrsOpenApiContextBuilder<>()
			.openApiConfiguration(openApiConfig)
			.buildContext(true);
		OpenAPI openApi = context.read();

		// serialization of the Swagger definition
		try {
			this.swaggerDefinition = Yaml.mapper().writeValueAsString(openApi);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		// require authentication to access the API documentation
		this.basicAuthenticator = BasicAuthenticator.fromSingleCredentials(
			configurationService.swaggerAccessUsername(),
			configurationService.swaggerAccessPassword(),
			"API plume-showcase"
		);
	}

	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public String get(@Context ContainerRequestContext requestContext) throws JsonProcessingException {
		basicAuthenticator.requireAuthentication(requestContext);

		return swaggerDefinition;
	}

}

