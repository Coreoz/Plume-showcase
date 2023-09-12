package com.coreoz.services.file;

import com.coreoz.db.generated.QUserFile;
import com.coreoz.plume.file.filetype.FileTypeDatabase;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.StringPath;

public enum ShowcaseFileType implements FileTypeDatabase {
    PICTURE(QUserFile.userFile, QUserFile.userFile.pictureUniqueName),
    EXCEL(QUserFile.userFile, QUserFile.userFile.excelUniqueName),
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
