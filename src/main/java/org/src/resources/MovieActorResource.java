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
import org.src.entities.MovieActor;

import java.net.URI;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/movie_actors")
public class MovieActorResource {
    @GET
    public List<MovieActor> list() {
        return MovieActor.listAll();
    }

    @GET
    @Path("/{id}")
    public MovieActor get(Long id) {
        return MovieActor.findById(id);
    }

    @POST
    @Transactional
    public Response create(MovieActor movieActor) {
        movieActor.persist();
        return Response.created(URI.create("/movie_actors/" + movieActor.id)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public MovieActor update(Long id, MovieActor movieActor) {
        MovieActor entity = MovieActor.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }

        entity.movieId = movieActor.movieId;
        entity.actorId = movieActor.actorId;

        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(Long id) {
        MovieActor entity = MovieActor.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
}
