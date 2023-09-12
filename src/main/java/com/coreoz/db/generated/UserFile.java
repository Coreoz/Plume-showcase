package com.coreoz.db.generated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import javax.annotation.processing.Generated;
import com.querydsl.sql.Column;

/**
 * UserFile is a Querydsl bean type
 */
@Generated("com.coreoz.plume.db.querydsl.generation.IdBeanSerializer")
public class UserFile {

    @Column("EXCEL_UNIQUE_NAME")
    private String excelUniqueName;

    @Column("PICTURE_UNIQUE_NAME")
    private String pictureUniqueName;

    @JsonSerialize(using=com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    @Column("USER_ID")
    private Long userId;

    public String getExcelUniqueName() {
        return excelUniqueName;
    }

    public void setExcelUniqueName(String excelUniqueName) {
        this.excelUniqueName = excelUniqueName;
    }

    public String getPictureUniqueName() {
        return pictureUniqueName;
    }

    public void setPictureUniqueName(String pictureUniqueName) {
        this.pictureUniqueName = pictureUniqueName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}

