package com.example.zti.service;

import com.example.zti.model.Recipe;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class RecipeService {

    @PersistenceContext(unitName = "kulinariaPU")
    private EntityManager em;

    public void save(Recipe recipe) {
        em.persist(recipe);
    }

    public List<Recipe> findAll() {
        return em.createQuery("SELECT r FROM Recipe r", Recipe.class).getResultList();
    }

    public void like(Long id) {
        Recipe recipe = em.find(Recipe.class, id);
        if (recipe != null) {
            recipe.setLikes(recipe.getLikes() + 1);
            em.merge(recipe);
        }
    }
}
