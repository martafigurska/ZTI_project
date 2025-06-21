package com.example.zti.resource;

import com.example.zti.model.Recipe;
import com.example.zti.model.User;
import com.example.zti.service.RecipeService;
import com.example.zti.service.UserService;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

@Path("/recipes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecipeEndpoint {

    @Inject
    private RecipeService recipeService;

    @GET
    public List<Recipe> getAll() {
        return recipeService.findAllWithLikes();
    }

    @Inject
    private UserService userService;

    @POST
    public Response addRecipe(Recipe recipe) {
        if (recipe.getTitle() == null || recipe.getAuthor() == null || recipe.getAuthor().getUsername() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Brak tytułu lub autora").build();
        }

        User managedAuthor = userService.findByUsername(recipe.getAuthor().getUsername());
        if (managedAuthor == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Nie znaleziono autora").build();
        }

        recipe.setAuthor(managedAuthor);

        recipeService.save(recipe);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteRecipe(@PathParam("id") Long id) {
        try {
            recipeService.deleteRecipeById(id);
            return Response.noContent().build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Błąd serwera: " + e.getMessage()).build();
        }
    }


    @POST
    @Path("/{id}/like")
    public Response like(@PathParam("id") Long id, User user) {
        try {
            recipeService.like(id, user.getUsername());
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Nie można polubić").build();
        }
    }
}
