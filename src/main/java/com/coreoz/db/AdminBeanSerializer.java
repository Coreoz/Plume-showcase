package com.coreoz.db;

import com.coreoz.plume.db.querydsl.generation.IdBeanSerializer;

public class AdminBeanSerializer extends IdBeanSerializer {
    public AdminBeanSerializer() {
        setUseJacksonAnnotation(true);
    }
}
