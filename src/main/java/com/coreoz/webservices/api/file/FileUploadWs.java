package com.coreoz.webservices.api.file;

import com.coreoz.plume.file.FileUploadWebJerseyService;
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

@Path("/admin/files")
@Tag(name = "Files", description = "Upload files")
@Consumes({MediaType.MULTIPART_FORM_DATA})
@Produces(MediaType.APPLICATION_JSON)
@PublicApi
@Singleton
public class FileUploadWs {
    private final FileUploadWebJerseyService fileUploadWebJerseyService;

    @Inject
    public FileUploadWs(FileUploadWebJerseyService fileService) {
        this.fileUploadWebJerseyService = fileService;
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
        return Response.ok(
                this.fileUploadWebJerseyService.add(
                    ShowcaseFileType.ENUM,
                    fileData,
                    fileMetadata
                )
            )
            .build();
    }
}
