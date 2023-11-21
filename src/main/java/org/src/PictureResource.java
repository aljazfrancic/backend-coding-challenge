package org.src;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/pictures")
public class PictureResource {
    @GET
    public List<Picture> list() {
        return Picture.listAll();
    }

    @GET
    @Path("/{id}")
    public Picture get(Long id) {
        return Picture.findById(id);
    } //TODO: also return actual file in separate GET request???


    @POST
    @Transactional
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response create(@RestForm @Schema(type = SchemaType.STRING, format = "binary") InputStream image, @RestForm @PartType(MediaType.APPLICATION_JSON) Movie movie) throws SQLException, IOException {
        Picture picture = new Picture();
        picture.movie = movie;
        picture.picture = image.readAllBytes();
        picture.persist();
        image.close();
        return Response.created(URI.create("/pictures/" + picture.id)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Picture update(Long id, Picture picture) {
        Picture entity = Picture.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }

        entity.id = picture.id;
        entity.movie = picture.movie;
        entity.picture = picture.picture;

        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(Long id) {
        Picture entity = Picture.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
}
