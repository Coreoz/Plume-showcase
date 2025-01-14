package com.coreoz.db.generated;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;

import com.querydsl.sql.ColumnMetadata;
import java.sql.Types;




/**
 * QUserFile is a Querydsl query type for UserFile
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class QUserFile extends com.querydsl.sql.RelationalPathBase<UserFile> {

    private static final long serialVersionUID = 1022868767;

    public static final QUserFile userFile = new QUserFile("SWC_USER_FILE");

    public final StringPath fileExcelFilename = createString("fileExcelFilename");

    public final StringPath filePictureFilename = createString("filePictureFilename");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final com.querydsl.sql.ForeignKey<com.coreoz.plume.admin.db.generated.AdminUser> constraint4 = createForeignKey(userId, "ID");

    public final com.querydsl.sql.ForeignKey<com.coreoz.plume.file.db.beans.FileMetadataQuerydsl> constraint4d = createForeignKey(filePictureFilename, "UNIQUE_NAME");

    public final com.querydsl.sql.ForeignKey<com.coreoz.plume.file.db.beans.FileMetadataQuerydsl> constraint4d0 = createForeignKey(fileExcelFilename, "UNIQUE_NAME");

    public QUserFile(String variable) {
        super(UserFile.class, forVariable(variable), "PLUME_DEMO", "SWC_USER_FILE");
        addMetadata();
    }

    public QUserFile(String variable, String schema, String table) {
        super(UserFile.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public QUserFile(String variable, String schema) {
        super(UserFile.class, forVariable(variable), schema, "SWC_USER_FILE");
        addMetadata();
    }

    public QUserFile(Path<? extends UserFile> path) {
        super(path.getType(), path.getMetadata(), "PLUME_DEMO", "SWC_USER_FILE");
        addMetadata();
    }

    public QUserFile(PathMetadata metadata) {
        super(UserFile.class, metadata, "PLUME_DEMO", "SWC_USER_FILE");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(fileExcelFilename, ColumnMetadata.named("FILE_EXCEL_FILENAME").withIndex(3).ofType(Types.VARCHAR).withSize(255).notNull());
        addMetadata(filePictureFilename, ColumnMetadata.named("FILE_PICTURE_FILENAME").withIndex(2).ofType(Types.VARCHAR).withSize(255).notNull());
        addMetadata(userId, ColumnMetadata.named("USER_ID").withIndex(1).ofType(Types.BIGINT).withSize(64).notNull());
    }

}

