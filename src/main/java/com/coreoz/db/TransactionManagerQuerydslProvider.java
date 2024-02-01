package com.coreoz.db;

import com.coreoz.plume.db.querydsl.transaction.TransactionManagerQuerydsl;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.MySQLTemplates;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.sql.DataSource;

@Singleton
public class TransactionManagerQuerydslProvider implements Provider<TransactionManagerQuerydsl> {
    private final TransactionManagerQuerydsl transactionManagerQuerydsl;
    @Inject
    public TransactionManagerQuerydslProvider(DataSource dataSource) {
        this.transactionManagerQuerydsl = new TransactionManagerQuerydsl(dataSource, new Configuration(MySQLTemplates.DEFAULT));
    }

    @Override
    public TransactionManagerQuerydsl get() {
        return transactionManagerQuerydsl;
    }
}
