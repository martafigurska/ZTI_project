package com.example.zti.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToOne
    private User author;

    @ManyToMany
    @JoinTable(
            name = "recipe_likes",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnoreProperties({"recipes", "passwordHash"}) // ukryj pola z User
    private List<User> likedByUsers = new ArrayList<>();


    // Gettery i settery
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public User getAuthor() { return author; }
    public void setAuthor(User author) { this.author = author; }
    public List<User> getLikedByUsers() { return likedByUsers; }
    public void setLikedByUsers(List<User> likedByUsers) { this.likedByUsers = likedByUsers; }
}
