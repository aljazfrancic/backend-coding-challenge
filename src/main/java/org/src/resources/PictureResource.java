package org.src.resources;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.util.List;

import org.jboss.resteasy.reactive.PartType;
import org.jboss.resteasy.reactive.RestForm;
import org.src.entities.Movie;
import org.src.entities.Picture;
import org.src.entities.RequestCounters;

@Transactional
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/pictures")
public class PictureResource {
    @GET
    public List<Picture> list() {
        RequestCounters requestCounters = RequestCounters.findById(1);
        requestCounters.pictureGet++;
        return Picture.listAll();
    }

    @GET
    @Path("/{id}")
    public Picture get(Long id) {
        RequestCounters requestCounters = RequestCounters.findById(1);
        requestCounters.pictureGetId++;
        return Picture.findById(id);
    }

    @GET
    @Path("/direct/{id}")
    public Response getDirect(Long id) throws IOException {
        RequestCounters requestCounters = RequestCounters.findById(1);
        requestCounters.pictureGetDirectId++;

        //get bytes
        byte[] imageBytes = ((Picture) Picture.findById(id)).picture;

        //guess mime type
        ByteArrayInputStream stream = new ByteArrayInputStream(imageBytes);
        String mime = URLConnection.guessContentTypeFromStream(stream);

        //send image bytes and correct mime type
        return Response.ok(imageBytes).type(mime).build();
    }

    //TODO: should be limited to "image/bmp", "image/png", "image/jpeg" and "image/gif" MIME types, but accepts any file
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response create(@RestForm InputStream file, @RestForm @PartType(MediaType.APPLICATION_JSON) Movie movie) throws IOException {
        RequestCounters requestCounters = RequestCounters.findById(1);
        requestCounters.picturePost++;
        Picture picture = new Picture();
        picture.movie = movie;
        picture.picture = file.readAllBytes();
        picture.persist();
        file.close();
        return Response.created(URI.create("/pictures/" + picture.id)).build();
    }

    @PUT
    @Path("/{id}")
    public Picture update(Long id, Picture picture) {
        RequestCounters requestCounters = RequestCounters.findById(1);
        requestCounters.picturePut++;
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
    public void delete(Long id) {
        RequestCounters requestCounters = RequestCounters.findById(1);
        requestCounters.pictureDelete++;
        Picture entity = Picture.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
}
