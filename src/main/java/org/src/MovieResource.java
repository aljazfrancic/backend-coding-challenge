package org.src;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/movies")
public class MovieResource {
    private static final long pageSize = 5;

    @GET
    public List<Movie> list() {
        return Movie.listAll();
    }

    @GET
    @Path("/{id}")
    public Movie get(Long id) {
        return Movie.findById(id);
    }

    @GET
    @Path("/page/{page}")
    public List<PanacheEntityBase> listPage(int page) {

        return Movie.streamAll().skip(page * pageSize).limit(pageSize).toList();
    }

    @POST
    @Transactional
    public Response create(Movie movie) {
        movie.persist();
        return Response.created(URI.create("/movies/" + movie.id)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Movie update(Long id, Movie movie) {
        Movie entity = Movie.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }

        entity.id = movie.id;
        entity.title = movie.title;
        entity.year = movie.year;
        entity.description = movie.description;

        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(Long id) {
        Movie entity = Movie.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

    @GET
    @Path("/search/{query}") //TODO TEST!
    public Movie search(String query) {
        return Movie.findByName(query);
    }
}
