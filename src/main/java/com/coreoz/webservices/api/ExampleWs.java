package com.coreoz.webservices.api;

import com.coreoz.plume.admin.jersey.feature.RestrictToAdmin;
import com.coreoz.plume.admin.services.permissions.AdminPermissions;
import com.coreoz.plume.admin.websession.WebSessionAdmin;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import com.coreoz.plume.jersey.security.permission.PublicApi;
import com.coreoz.services.configuration.ConfigurationService;
import com.coreoz.webservices.api.data.Test;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;

@Path("/example")
@Tag(name = "example", description = "Manage exemple web-services")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@PublicApi
@Singleton
@Slf4j
public class ExampleWs {
	private final ConfigurationService configurationService;

	@Inject
	public ExampleWs(ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}

	@GET
	@Path("/test/{name}")
	@Operation(description = "Example web-service")
	public Test test(@Parameter(required = true) @PathParam("name") String name) {
		return new Test("hello " + name + "\n" + configurationService.hello());
	}

	@GET
	@Path("/other")
    @RestrictToAdmin(AdminPermissions.MANAGE_USERS)
	@Operation(description = "Other Example web-service")
	public Test test(@Context WebSessionAdmin webSessionAdmin) {
		return new Test("hello " + webSessionAdmin.getFullName() + "\n" + configurationService.hello());
	}

	@POST
	@Path("/no-answer")
    @RestrictToAdmin(AdminPermissions.MANAGE_USERS)
	@Operation(description = "No response")
	public void noAnswer(@Context WebSessionAdmin webSessionAdmin) {
		logger.info("Hello {}", webSessionAdmin.getFullName());
	}
}
