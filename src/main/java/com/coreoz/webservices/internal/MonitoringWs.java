package com.coreoz.webservices.internal;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.coreoz.db.HikariMetricsDataSourceProvider;
import com.coreoz.plume.db.transaction.TransactionManager;
import com.coreoz.plume.jersey.monitoring.utils.health.HealthCheckBuilder;
import com.coreoz.plume.jersey.monitoring.utils.health.beans.HealthStatus;
import com.coreoz.plume.jersey.monitoring.utils.info.ApplicationInfoProvider;

import com.coreoz.plume.jersey.monitoring.utils.info.beans.ApplicationInfo;
import com.coreoz.plume.jersey.monitoring.utils.metrics.MetricsCheckBuilder;
import com.coreoz.plume.jersey.security.basic.BasicAuthenticator;
import com.coreoz.plume.jersey.security.permission.PublicApi;
import org.apache.commons.lang3.tuple.ImmutablePair;

@Path("/monitoring")
// Authentication is done directly by the web service without any annotation
@PublicApi
@Produces(MediaType.APPLICATION_JSON)
@Singleton
public class MonitoringWs {
    private final ApplicationInfo applicationInfo;
    private final Provider<HealthStatus> healthStatusProvider;
    private final Provider<Map<String, Metric>> metricsStatusProvider;

    @org.jetbrains.annotations.NotNull
    private final HikariMetricsDataSourceProvider hikariMetricsDataSourceProvider;
    private final BasicAuthenticator<String> basicAuthenticator;

    @Inject
    public MonitoringWs(
        ApplicationInfoProvider applicationInfoProvider,
        TransactionManager transactionManager,
        HikariMetricsDataSourceProvider hikariMetricsDataSourceProvider,
        InternalApiAuthenticator apiAuthenticator
    ) {
        this.applicationInfo = applicationInfoProvider.get();
        // Registering health checks
        this.healthStatusProvider = new HealthCheckBuilder()
            .registerDatabaseHealthCheck(transactionManager)
            .build();

        // Registering metrics to monitor
        this.metricsStatusProvider = new MetricsCheckBuilder()
            .registerJvmMetrics()
            .registerMetric("db-pool", hikariMetricsDataSourceProvider.getHikariMetricsRegistry())
            .build();
        this.hikariMetricsDataSourceProvider = hikariMetricsDataSourceProvider;

        // Require authentication to access monitoring endpoints
        this.basicAuthenticator = apiAuthenticator.get();
    }

    @GET
    @Path("/info")
    public ApplicationInfo info(@Context ContainerRequestContext requestContext) {
        basicAuthenticator.requireAuthentication(requestContext);
        return this.applicationInfo;
    }

    @GET
    @Path("/health")
    public HealthStatus health(@Context ContainerRequestContext requestContext) {
        basicAuthenticator.requireAuthentication(requestContext);
        return this.healthStatusProvider.get();
    }

    @GET
    @Path("/metrics")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Metric> metrics(@Context ContainerRequestContext requestContext) {
        // basicAuthenticator.requireAuthentication(requestContext);
        List<ImmutablePair<String, Object>> metrics = hikariMetricsDataSourceProvider
            .getHikariMetricsRegistry()
            .getGauges()
            .entrySet()
            .stream()
            .map(entry -> new ImmutablePair<>(entry.getKey(), entry.getValue().getValue()))
            .toList();
        System.out.println(metrics);
        SortedMap<String, Gauge> gauges = hikariMetricsDataSourceProvider
            .getHikariMetricsRegistry()
            .getGauges();
        System.out.println(gauges.get("HikariPool-1.pool.ActiveConnections").getValue());
        System.out.println(gauges.get("HikariPool-1.pool.IdleConnections").getValue());
        System.out.println(gauges.get("HikariPool-1.pool.MaxConnections").getValue());
        System.out.println(gauges.get("HikariPool-1.pool.MinConnections").getValue());
        System.out.println(gauges.get("HikariPool-1.pool.PendingConnections").getValue());
        System.out.println(gauges.get("HikariPool-1.pool.TotalConnections").getValue());
        return metricsStatusProvider.get();
    }
}
