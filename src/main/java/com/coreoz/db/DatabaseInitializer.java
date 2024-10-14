package com.coreoz.db;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;

/**
 * Initialize the H2 database with SQL scripts placed in src/main/resources/db/migration
 */
@Singleton
public class DatabaseInitializer {

	private final DataSource dataSource;

	@Inject
	public DatabaseInitializer(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setup() {
		Flyway
			.configure()
			.dataSource(dataSource)
			.outOfOrder(true)
			.load()
			.migrate();
	}
}
