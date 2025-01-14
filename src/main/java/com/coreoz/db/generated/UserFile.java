package com.coreoz.db.generated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javax.annotation.processing.Generated;
import com.querydsl.sql.Column;

/**
 * UserFile is a Querydsl bean type
 */
@Generated("com.coreoz.plume.db.querydsl.generation.IdBeanSerializer")
public class UserFile {

    @Column("FILE_EXCEL_FILENAME")
    private String fileExcelFilename;

    @Column("FILE_PICTURE_FILENAME")
    private String filePictureFilename;

    @JsonSerialize(using=com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    @Column("USER_ID")
    private Long userId;

    public String getFileExcelFilename() {
        return fileExcelFilename;
    }

    public void setFileExcelFilename(String fileExcelFilename) {
        this.fileExcelFilename = fileExcelFilename;
    }

    public String getFilePictureFilename() {
        return filePictureFilename;
    }

    public void setFilePictureFilename(String filePictureFilename) {
        this.filePictureFilename = filePictureFilename;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}

