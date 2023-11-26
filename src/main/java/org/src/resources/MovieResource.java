package org.src.resources;

import jakarta.persistence.LockModeType;
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
        RequestCounters.getPessimisticWriteLockedRequestCounters().movieGet++;
        return Movie.listAll();
    }

    @GET
    @Path("/{id}")
    public Movie get(Long id) {
        RequestCounters.getPessimisticWriteLockedRequestCounters().movieGetId++;
        return Movie.findById(id);
    }

    @GET
    @Path("/page/{page}")
    public List<Movie> listPage(int page) {
        RequestCounters.getPessimisticWriteLockedRequestCounters().movieGetPage++;
        return Movie.streamAll().skip(page * pageSize).limit(pageSize).map(Movie.class::cast).toList();
    }

    @POST
    public Response create(Movie movie) {
        RequestCounters.getPessimisticWriteLockedRequestCounters().moviePost++;
        movie.persist();
        return Response.created(URI.create("/movies/" + movie.id)).build();
    }

    @PUT
    @Path("/{id}")
    public Movie update(Long id, Movie movie) {
        RequestCounters.getPessimisticWriteLockedRequestCounters().moviePut++;
        Movie entity = Movie.findById(id, LockModeType.PESSIMISTIC_WRITE);
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
        RequestCounters.getPessimisticWriteLockedRequestCounters().movieDelete++;
        Movie entity = Movie.findById(id, LockModeType.PESSIMISTIC_WRITE);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

    @GET
    @Path("/search/{query}")
    public List<Movie> search(String query) {
        RequestCounters.getPessimisticWriteLockedRequestCounters().movieSearch++;
        return Movie.find("title like ?1 or description like ?1 or year like ?1", "%" + query + "%").withLock(LockModeType.PESSIMISTIC_READ).stream().map(Movie.class::cast).toList();
    }
}
