package com.coreoz.jersey;

import com.coreoz.plume.admin.jersey.feature.AdminSecurityFeature;
import com.coreoz.plume.admin.jersey.feature.RestrictToAdmin;
import com.coreoz.plume.admin.websession.WebSessionAdmin;
import com.coreoz.plume.admin.websession.WebSessionPermission;
import com.coreoz.plume.admin.websession.jersey.WebSessionAdminFactory;
import com.coreoz.plume.jersey.errors.WsJacksonJsonProvider;
import com.coreoz.plume.jersey.errors.WsResultExceptionMapper;
import com.coreoz.plume.jersey.java8.TimeParamProvider;
import com.coreoz.plume.jersey.security.permission.PublicApi;
import com.coreoz.plume.jersey.security.permission.RequireExplicitAccessControlFeature;
import com.coreoz.plume.jersey.security.size.ContentSizeLimitFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.process.internal.RequestScoped;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Jersey configuration
 */
@Singleton
public class JerseyConfigProvider implements Provider<ResourceConfig> {

	private final ResourceConfig config;

	@Inject
	public JerseyConfigProvider(ObjectMapper objectMapper) {
		config = new ResourceConfig();

		// this package will be scanned by Jersey to discover web-service classes
		config.packages("com.coreoz.webservices");
		// admin web-services
		config.packages("com.coreoz.plume.admin.webservices");
		// plume file
		config.packages("com.coreoz.plume.file.webservices");
		// enable to fetch the current user as a web-service parameter
		config.register(new AbstractBinder() {
			@Override
			protected void configure() {
				bindFactory(WebSessionAdminFactory.class).to(WebSessionPermission.class).in(RequestScoped.class);
				bindFactory(WebSessionAdminFactory.class).to(WebSessionAdmin.class).in(RequestScoped.class);
			}
		});

		// filters configuration
		// handle errors and exceptions
		config.register(WsResultExceptionMapper.class);
        // Limit request body size to 500kb
        config.register(ContentSizeLimitFeature.class);
		// require explicit access control on API
		config.register(RequireExplicitAccessControlFeature.accessControlAnnotations(PublicApi.class, RestrictToAdmin.class));
		// admin web-services protection with the permission system
		config.register(AdminSecurityFeature.class);
		// to debug web-service requests
		// register(LoggingFeature.class);
		// To use Multipart for file upload
		config.register(MultiPartFeature.class);

		// java 8
		config.register(TimeParamProvider.class);

		// WADL is like swagger for jersey
		// by default it should be disabled to prevent leaking API documentation
		config.property("jersey.config.server.wadl.disableWadl", true);

		// jackson mapper configuration
		WsJacksonJsonProvider jacksonProvider = new WsJacksonJsonProvider();
		jacksonProvider.setMapper(objectMapper);
		config.register(jacksonProvider);
	}

	@Override
	public ResourceConfig get() {
		return config;
	}

}
