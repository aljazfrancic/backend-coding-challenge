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
import org.src.entities.Movie;
import org.src.entities.RequestCounters;

import java.net.URI;
import java.util.List;

@Transactional
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/movies")
public class MovieResource {
    private static final long pageSize = 5;

    @GET
    public List<Movie> list() {
        RequestCounters requestCounters = RequestCounters.findById(1);
        requestCounters.movieGet++;
        return Movie.listAll();
    }

    @GET
    @Path("/{id}")
    public Movie get(Long id) {
        RequestCounters requestCounters = RequestCounters.findById(1);
        requestCounters.movieGetId++;
        return Movie.findById(id);
    }

    @GET
    @Path("/page/{page}")
    public List<Movie> listPage(int page) {
        RequestCounters requestCounters = RequestCounters.findById(1);
        requestCounters.movieGetPage++;
        return Movie.streamAll().skip(page * pageSize).limit(pageSize).map(Movie.class::cast).toList();
    }

    @POST
    public Response create(Movie movie) {
        RequestCounters requestCounters = RequestCounters.findById(1);
        requestCounters.moviePost++;
        movie.persist();
        return Response.created(URI.create("/movies/" + movie.id)).build();
    }

    @PUT
    @Path("/{id}")
    public Movie update(Long id, Movie movie) {
        RequestCounters requestCounters = RequestCounters.findById(1);
        requestCounters.moviePut++;
        Movie entity = Movie.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }

        entity.title = movie.title;
        entity.year = movie.year;
        entity.description = movie.description;

        return entity;
    }

    @DELETE
    @Path("/{id}")
    public void delete(Long id) {
        RequestCounters requestCounters = RequestCounters.findById(1);
        requestCounters.movieDelete++;
        Movie entity = Movie.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

    @GET
    @Path("/search/{query}") //TODO TEST!
    public Movie search(String query) {
        RequestCounters requestCounters = RequestCounters.findById(1);
        requestCounters.movieSearch++;
        return Movie.findByName(query);
    }
}
