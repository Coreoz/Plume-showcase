package com.coreoz.webservices.api.file;

import com.coreoz.plume.file.FileUploadWebJerseyService;
import com.coreoz.plume.file.services.mimetype.FileMimeTypeDetector;
import com.coreoz.plume.file.validator.FileUploadData;
import com.coreoz.plume.file.validator.FileUploadValidator;
import com.coreoz.plume.jersey.security.permission.PublicApi;
import com.coreoz.services.file.ShowcaseFileType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
    @RequestBody(content = @Content(schema = @Schema(implementation = FileUploadRequest.class)))
    public Response uploadPicture(@BeanParam FileUploadRequest request) {
        FileUploadData fileUploadMetadata = FileUploadValidator.from(
                request.getFileMetadata(),
                request.getFileData(),
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
    @RequestBody(content = @Content(schema = @Schema(implementation = FileUploadRequest.class)))
    public Response uploadExcelFile(@BeanParam FileUploadRequest request) {
        FileUploadData fileUploadMetadata = FileUploadValidator.from(
                request.getFileMetadata(),
                request.getFileData(),
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
