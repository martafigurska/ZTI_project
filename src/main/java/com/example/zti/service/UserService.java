package com.example.zti.service;

import com.example.zti.model.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Stateless
public class UserService {

    @PersistenceContext(unitName = "kulinariaPU")
    private EntityManager em;

    public void register(User user) {
        user.setPasswordHash(hash(user.getPasswordHash()));
        em.persist(user);
    }

    public boolean validate(String username, String password) {
        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return user.getPasswordHash().equals(hash(password));
        } catch (NoResultException e) {
            return false;
        }
    }

    private String hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
