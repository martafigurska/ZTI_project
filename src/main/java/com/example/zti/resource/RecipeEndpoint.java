package com.example.zti.resource;

import com.example.zti.model.Recipe;
import com.example.zti.service.RecipeService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/recipes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecipeEndpoint {   // zmieniona nazwa klasy

    @Inject
    private RecipeService recipeService;

    @POST
    public Response addRecipe(Recipe recipe) {
        recipeService.save(recipe);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public List<Recipe> getAll() {
        return recipeService.findAll();
    }

    @POST
    @Path("/{id}/like")
    public Response like(@PathParam("id") Long id) {
        recipeService.like(id);
        return Response.ok().build();
    }
}
