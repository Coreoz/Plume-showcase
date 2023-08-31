package com.coreoz;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import com.coreoz.plume.admin.services.scheduler.LogApiScheduledJobs;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coreoz.db.DatabaseInitializer;
import com.coreoz.guice.ApplicationModule;
import com.coreoz.jersey.GrizzlySetup;
import com.coreoz.plume.jersey.guice.JerseyGuiceFeature;
import com.coreoz.wisp.Scheduler;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;

/**
 * The application entry point, where it all begins.
 */
public class WebApplication {
	private static final Logger logger = LoggerFactory.getLogger(WebApplication.class);

	// maximal waiting time for the last process to execute after the JVM received a kill signal
	public static final Duration GRACEFUL_SHUTDOWN_TIMEOUT = Duration.ofSeconds(60);

	public static void main(String[] args) {
		try {
			long startTimestamp = System.currentTimeMillis();

			// initialize all application objects with Guice
			Injector injector = Guice.createInjector(Stage.PRODUCTION, new ApplicationModule());

			// initialize database
			injector.getInstance(DatabaseInitializer.class).setup();

			// schedule jobs
			// configure logApi
			injector.getInstance(LogApiScheduledJobs.class).scheduleJobs();

			// configuration Jersey for API exposition
			ResourceConfig jerseyResourceConfig = injector.getInstance(ResourceConfig.class);
			// enable Jersey to create objects through Guice Injector instance
			jerseyResourceConfig.register(new JerseyGuiceFeature(injector));
			// starts the server
			HttpServer httpServer = GrizzlySetup.start(
				jerseyResourceConfig,
				System.getProperty("http.port"),
				System.getProperty("http.address")
			);

			// Add a shutdown hook to execute some code when the JVM receive a kill signal before it stops
			addShutDownListener(httpServer, injector.getInstance(Scheduler.class));

			logger.info("Server started in {} ms", System.currentTimeMillis() - startTimestamp);
		} catch (Throwable e) {
			logger.error("Failed to start the server", e);
			// This line is important, because during initialization some libraries change the main thread type
			// to daemon, which mean that even if the project is completely down, the JVM is not stopped.
			// Stopping the JVM is important to enable production supervision tools to detect and restart the project.
			System.exit(1);
		}
	}


	private static void addShutDownListener(HttpServer httpServer, Scheduler scheduler) {
		Runtime.getRuntime().addShutdownHook(new Thread(
			() -> {
				logger.info("Stopping signal received, shutting down server and scheduler...");
				GrizzlyFuture<HttpServer> grizzlyServerShutdownFuture = httpServer.shutdown(GRACEFUL_SHUTDOWN_TIMEOUT.toSeconds(), TimeUnit.SECONDS);
					try {
						logger.info("Waiting for server to shut down... Shutdown timeout is {} seconds", GRACEFUL_SHUTDOWN_TIMEOUT.toSeconds());
						scheduler.gracefullyShutdown(GRACEFUL_SHUTDOWN_TIMEOUT);
						grizzlyServerShutdownFuture.get();
					} catch(Exception e) {
						logger.error("Error while shutting down server.", e);
					}
				logger.info("Server and scheduler stopped.");
			},
			"shutdownHook"
		));
	}
}
