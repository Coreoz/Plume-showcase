package com.coreoz.db;

import com.codahale.metrics.MetricRegistry;
import com.typesafe.config.Config;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Singleton
public class HikariMetricsDataSourceProvider implements Provider<DataSource> {
    private final HikariDataSource hikariDataSource;
    private final MetricRegistry hikariMetricsRegistry = new MetricRegistry();

    @Inject
    private HikariMetricsDataSourceProvider(Config config) {
        HikariConfig hikariConfig = new HikariConfig(mapToProperties(readConfig(config, "db.hikari")));
        hikariConfig.setMetricRegistry(hikariMetricsRegistry);
        this.hikariDataSource = new HikariDataSource(hikariConfig);
    }

    public DataSource get() {
        return hikariDataSource;
    }

    public MetricRegistry getHikariMetricsRegistry() {
        return hikariMetricsRegistry;
    }

    // internal

    private static Properties mapToProperties(Map<String, String> mapProperties) {
        Properties properties = new Properties();
        properties.putAll(mapProperties);
        return properties;
    }

    private static Map<String, String> readConfig(Config config, String prefix) {
        return config
            .getObject(prefix)
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                value -> value.getValue().unwrapped().toString()
            ));
    }
}
