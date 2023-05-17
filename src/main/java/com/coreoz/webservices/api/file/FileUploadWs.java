package com.coreoz.webservices.api.file;

import com.coreoz.plume.file.FileUploadWebJerseyService;
import com.coreoz.plume.file.services.mimetype.FileMimeTypeDetector;
import com.coreoz.plume.file.validator.FileUploadData;
import com.coreoz.plume.file.validator.FileUploadValidator;
import com.coreoz.plume.jersey.errors.Validators;
import com.coreoz.plume.jersey.security.permission.PublicApi;
import com.coreoz.services.file.ShowcaseFileType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.Set;

@Path("/admin/files")
@Tag(name = "Files", description = "Upload files")
@Consumes({MediaType.MULTIPART_FORM_DATA})
@Produces(MediaType.APPLICATION_JSON)
@PublicApi
@Singleton
public class FileUploadWs {
    private final FileUploadWebJerseyService fileUploadWebJerseyService;
    private final FileMimeTypeDetector fileMimeTypeDetector;

    @Inject
    public FileUploadWs(FileUploadWebJerseyService fileService, FileMimeTypeDetector fileMimeTypeDetector) {
        this.fileUploadWebJerseyService = fileService;
        this.fileMimeTypeDetector = fileMimeTypeDetector;
    }

    @POST
    @Operation(description = "Upload a file")
    public Response upload(
        @Context ContainerRequestContext context,
        @FormDataParam("file") FormDataBodyPart fileMetadata,
        @FormDataParam("file") InputStream fileData
    ) {
        Validators.checkRequired("fileMetadata", fileMetadata);
        Validators.checkRequired("file", fileData);
        FileUploadData fileUploadMetadata = FileUploadValidator.from(fileMetadata, fileData, this.fileMimeTypeDetector)
            .fileMaxSize(2_000_000)
            .fileNameAllowEmpty()
            .fileNameMaxLength(255)
            .fileExtensionAllowEmpty()
            .fileExtensions(Set.of("hprof"))
            /*.fileTypeNotEmpty()
            .mimeTypes(Set.of("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/vnd.ms-excel"))*/
            .finish();
        return Response.ok(
                this.fileUploadWebJerseyService.add(
                    ShowcaseFileType.ENUM,
                    fileUploadMetadata
                )
            )
            .build();
    }
}
