package com.coreoz.webservices.api.file;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.InputStream;

@Data
public class FileUploadRequest {
    @Hidden
    @FormDataParam("file")
    private FormDataBodyPart fileMetadata;

    @Schema(name = "file", type = "string", format = "binary")
    @FormDataParam("file")
    private InputStream fileData;
}
