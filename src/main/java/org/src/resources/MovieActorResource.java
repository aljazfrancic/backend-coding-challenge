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
import org.src.entities.MovieActor;
import org.src.entities.RequestCounters;

import java.net.URI;
import java.util.List;

@Transactional
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/movie_actors")
public class MovieActorResource {
    @GET
    public List<MovieActor> list() {
        RequestCounters.getPessimicticWriteLockedRequestCounters().movieActorGet++;
        return MovieActor.listAll();
    }

    @GET
    @Path("/{id}")
    public MovieActor get(Long id) {
        RequestCounters.getPessimicticWriteLockedRequestCounters().movieActorGetId++;
        return MovieActor.findById(id);
    }

    @POST
    public Response create(MovieActor movieActor) {
        RequestCounters.getPessimicticWriteLockedRequestCounters().movieActorPost++;
        movieActor.persist();
        return Response.created(URI.create("/movie_actors/" + movieActor.id)).build();
    }

    @PUT
    @Path("/{id}")
    public MovieActor update(Long id, MovieActor movieActor) {
        RequestCounters.getPessimicticWriteLockedRequestCounters().movieActorPut++;
        MovieActor entity = MovieActor.findById(id, LockModeType.PESSIMISTIC_WRITE);
        if (entity == null) {
            throw new NotFoundException();
        }

        entity.movieId = movieActor.movieId;
        entity.actorId = movieActor.actorId;

        return entity;
    }

    @DELETE
    @Path("/{id}")
    public void delete(Long id) {
        RequestCounters.getPessimicticWriteLockedRequestCounters().movieActorDelete++;
        MovieActor entity = MovieActor.findById(id, LockModeType.PESSIMISTIC_WRITE);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
}
