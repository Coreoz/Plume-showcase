package com.coreoz.db;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.flywaydb.core.Flyway;

/**
 * Initialize the H2 database with SQL scripts placed in src/main/resources/db/migration
 */
@Singleton
public class InitializeDatabase {

	private final DataSource dataSource;

	@Inject
	public InitializeDatabase(DataSource dataSource) {
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
