package com.coreoz.db;

import com.coreoz.plume.conf.guice.GuiceConfModule;
import com.coreoz.plume.db.guice.DataSourceModule;
import com.coreoz.plume.db.transaction.TransactionManager;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.codegen.MetaDataExporter;
import com.querydsl.sql.codegen.MetadataExporterConfigImpl;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.sql.SQLException;

/**
 * Generate Querydsl classes for the database layer.
 *
 * Run the {@link #main(String...)} method from your IDE to regenerate Querydsl classes.
 */
@Slf4j
public class QuerydslGenerator {
	static final String TABLES_PREFIX = "SWC_";

	public static void main(String... args) {
		Configuration configuration = new Configuration(SQLTemplates.DEFAULT);
		configuration.registerNumeric(1, 0, Boolean.class);
		configuration.registerNumeric(9, 0, Long.class);
		configuration.registerNumeric(19, 0, Long.class);

        MetaDataExporter exporter = makeMetaDataExporter(configuration);

        Injector injector = Guice.createInjector(new GuiceConfModule(), new DataSourceModule());
        injector.getInstance(DatabaseInitializer.class).setup();
		injector.getInstance(TransactionManager.class).execute(connection -> {
			try {
				exporter.export(connection.getMetaData());
			} catch (SQLException e) {
				logger.error("Querydsl database objects generation failed", e);
			}
		});
	}

    @NotNull
    private static MetaDataExporter makeMetaDataExporter(Configuration configuration) {
        MetadataExporterConfigImpl exporterConfig = new MetadataExporterConfigImpl();
        exporterConfig.setPackageName("com.coreoz.db.generated");
        exporterConfig.setTargetFolder(new File("src/main/java"));
        exporterConfig.setTableNamePattern(TABLES_PREFIX + "%");
        exporterConfig.setNamingStrategyClass(AdminNamingStrategy.class);
        exporterConfig.setBeanSerializerClass(AdminBeanSerializer.class);
        exporterConfig.setColumnAnnotations(true);
        MetaDataExporter exporter = new MetaDataExporter(exporterConfig);
        exporter.setConfiguration(configuration);
        return exporter;
    }
}
