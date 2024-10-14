package com.coreoz.webservices.api.file;

import com.coreoz.plume.file.FileUploadWebJerseyService;
import com.coreoz.plume.file.services.mimetype.FileMimeTypeDetector;
import com.coreoz.plume.file.validator.FileUploadData;
import com.coreoz.plume.file.validator.FileUploadValidator;
import com.coreoz.plume.jersey.security.permission.PublicApi;
import com.coreoz.services.file.ShowcaseFileType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.InputStream;
import java.util.Set;

@Path("/files")
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

    /**
     * Example of webservice that only accepts JPEG files
     */
    @POST
    @Path("/pictures")
    @Operation(description = "Upload a file")
    public Response uploadPicture(
        @Context ContainerRequestContext context,
        @FormDataParam("file") FormDataBodyPart fileMetadata,
        @FormDataParam("file") InputStream fileData
    ) {
        FileUploadData fileUploadMetadata = FileUploadValidator.from(
                fileMetadata,
                fileData,
                this.fileMimeTypeDetector
            )
            .fileMaxSize(2_000_000)
            .fileNameAllowEmpty()
            .fileNameMaxLength(255)
            .fileImage()
            .keepOriginalFileName()
            .finish();
        return Response.ok(
                this.fileUploadWebJerseyService.add(
                    ShowcaseFileType.PICTURE,
                    fileUploadMetadata
                )
            )
            .build();
    }

    /**
     * Example of webservice that only accepts Excel files
     */
    @POST
    @Path("/reports")
    @Operation(description = "Upload a file")
    public Response uploadExcelFile(
        @Context ContainerRequestContext context,
        @FormDataParam("file") FormDataBodyPart fileMetadata,
        @FormDataParam("file") InputStream fileData
    ) {
        FileUploadData fileUploadMetadata = FileUploadValidator.from(
                fileMetadata,
                fileData,
                this.fileMimeTypeDetector
            )
            .fileMaxSize(2_000_000)
            .fileNameAllowEmpty()
            .fileNameMaxLength(255)
            .fileExtensionNotEmpty()
            .fileExtensions(Set.of("xls", "xlsx"))
            // Or mime type
            //.fileTypeNotEmpty()
            //.mimeTypes(Set.of(
            //    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            //    "application/vnd.ms-excel"
            //))
            .sanitizeFileName()
            .finish();
        return Response.ok(
                this.fileUploadWebJerseyService.add(
                    ShowcaseFileType.EXCEL,
                    fileUploadMetadata
                )
            )
            .build();
    }
}
