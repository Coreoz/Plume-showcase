package com.coreoz.db;

import com.coreoz.plume.admin.db.generated.QAdminUser;
import com.coreoz.plume.file.db.beans.QFileDataQueryDsl;
import com.coreoz.plume.file.db.beans.QFileMetadataQuerydsl;
import com.querydsl.codegen.EntityType;
import com.querydsl.sql.codegen.DefaultNamingStrategy;

import java.util.Locale;

import static com.coreoz.db.QuerydslGenerator.TABLES_PREFIX;

public class AdminNamingStrategy extends DefaultNamingStrategy {
    @Override
    public String getClassName(String tableName) {
        // uncomment if you are using plume file
        if("plm_file_data".equalsIgnoreCase(tableName)) {
            return QFileDataQueryDsl.class.getName();
        }
        if("plm_file".equalsIgnoreCase(tableName)) {
            return QFileMetadataQuerydsl.class.getName();
        }
        if("plm_user".equalsIgnoreCase(tableName)) {
            return QAdminUser.class.getName();
        }
        return super.getClassName(tableName.substring(TABLES_PREFIX.length()));
    }

    @Override
    public String getDefaultVariableName(EntityType entityType) {
        String variableName = getClassName(entityType.getData().get("table").toString());
        return variableName.substring(0, 1).toLowerCase(Locale.ENGLISH) + variableName.substring(1);
    }
}
