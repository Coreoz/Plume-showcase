package com.coreoz.guice;

import com.coreoz.jersey.JerseyConfigProvider;
import com.coreoz.plume.admin.guice.GuiceAdminWsModule;
import com.coreoz.plume.admin.services.permissions.AdminPermissionService;
import com.coreoz.plume.admin.websession.JwtSessionSigner;
import com.coreoz.plume.admin.websession.JwtSessionSignerProvider;
import com.coreoz.plume.admin.websession.WebSessionSigner;
import com.coreoz.plume.conf.guice.GuiceConfModule;
import com.coreoz.plume.db.querydsl.guice.GuiceQuerydslModule;
import com.coreoz.plume.file.filetype.FileTypesProvider;
import com.coreoz.plume.file.guice.GuiceFileDownloadModule;
import com.coreoz.plume.file.guice.GuiceFileMetadataDatabaseModule;
import com.coreoz.plume.file.guice.GuiceFileModule;
import com.coreoz.plume.file.guice.GuiceFileStorageDatabaseModule;
import com.coreoz.plume.jersey.guice.GuiceJacksonModule;
import com.coreoz.plume.scheduler.guice.GuiceSchedulerModule;
import com.coreoz.services.file.ShowCaseFileTypesProvider;
import com.coreoz.webservices.admin.permissions.ProjectAdminPermissionService;
import com.google.inject.AbstractModule;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Group the Guice modules to install in the application
 */
public class ApplicationModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new GuiceConfModule());
		install(new GuiceJacksonModule());
		install(new GuiceQuerydslModule());
		// admin module
		install(new GuiceAdminWsModule());
		bind(WebSessionSigner.class).toProvider(JwtSessionSignerProvider.class);
		bind(JwtSessionSigner.class).toProvider(JwtSessionSignerProvider.class);
		bind(AdminPermissionService.class).to(ProjectAdminPermissionService.class);
		// API log configuration
		install(new GuiceSchedulerModule());

		// Plume File
		install(new GuiceFileMetadataDatabaseModule());
		install(new GuiceFileStorageDatabaseModule());
		install(new GuiceFileDownloadModule());
		install(new GuiceFileModule());
		bind(FileTypesProvider.class).to(ShowCaseFileTypesProvider.class);

		// prepare Jersey configuration
		bind(ResourceConfig.class).toProvider(JerseyConfigProvider.class);
	}

}
