package org.src.resources;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import org.src.entities.Movie;
import org.src.entities.Picture;

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
    public Response create(@RestForm InputStream file, @RestForm @PartType(MediaType.APPLICATION_JSON) Movie movie) throws IOException {
        //TODO: should allow only pictures, but allows any files
        Picture picture = new Picture();
        picture.movie = movie;
        picture.picture = file.readAllBytes();
        picture.persist();
        file.close();
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
