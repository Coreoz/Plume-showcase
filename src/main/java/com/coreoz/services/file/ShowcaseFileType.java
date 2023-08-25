package com.coreoz.services.file;

import com.coreoz.plume.file.filetype.FileTypeDatabase;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.StringPath;

public enum ShowcaseFileType implements FileTypeDatabase {
    // could be ENUM(QUserFile.userFile, QUserFile.userFile.fileUniqueName)
    ENUM(null, null)
    ;

    private final EntityPath<?> fileEntity;
    private final StringPath joinColumn;

    ShowcaseFileType(EntityPath<?> fileEntity, StringPath joinColumn) {
        this.fileEntity = fileEntity;
        this.joinColumn = joinColumn;
    }


    @Override
    public EntityPath<?> getFileEntity() {
        return fileEntity;
    }

    @Override
    public StringPath getJoinColumn() {
        return joinColumn;
    }
}
