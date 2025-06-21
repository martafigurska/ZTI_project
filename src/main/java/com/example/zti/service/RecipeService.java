package com.example.zti.service;

import com.example.zti.model.Recipe;
import com.example.zti.model.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.List;

@Stateless
public class RecipeService {

    @PersistenceContext(unitName = "kulinariaPU")
    private EntityManager em;

    public void save(Recipe recipe) {
        em.persist(recipe);
    }

    public Recipe findById(Long id) {
        return em.find(Recipe.class, id);
    }

//    @Transactional
//    public void delete(Recipe recipe) {
//        // Upewnij się, że encja jest zarządzana
//        Recipe managed = em.contains(recipe) ? recipe : em.merge(recipe);
//        em.remove(managed);
//    }
    @Transactional
    public void deleteRecipeById(Long id) {
        Recipe recipe = em.find(Recipe.class, id);
        if (recipe != null) {
            em.remove(recipe);
        } else {
            throw new EntityNotFoundException("Przepis o id " + id + " nie istnieje");
        }
    }

    public List<Recipe> findAll() {
        return em.createQuery("SELECT r FROM Recipe r", Recipe.class).getResultList();
    }

    public List<Recipe> findAllWithLikes() {
        return em.createQuery(
                "SELECT DISTINCT r FROM Recipe r LEFT JOIN FETCH r.likedByUsers LEFT JOIN FETCH r.author",
                Recipe.class
        ).getResultList();
    }
    public void like(Long recipeId, String username) {
        Recipe recipe = em.find(Recipe.class, recipeId);
        User user = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username).getSingleResult();

        if (recipe != null && user != null && !recipe.getLikedByUsers().contains(user)) {
            recipe.getLikedByUsers().add(user);
            em.merge(recipe);
        }
    }
}
