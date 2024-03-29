package com.project.cookshare.models;

import lombok.*;
import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Add likes and dislikes fields
    @Column(nullable = true)
    private Integer likes = 0; // Initialize with 0

    @Column(nullable = true)
    private Integer dislikes = 0; // Initialize with 0

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    public void setId(Integer id) {
        this.id = id;
    }
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    public void setLikes(Integer likes) {
        this.likes = likes;
    }
    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

}
