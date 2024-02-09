package com.project.cookshare.models;

import lombok.*;
import jakarta.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "recipes_submitted")
    private int recipesSubmitted;

    // Mapping to comments, recipes, ratings, and favorites
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Set<Recipe> recipes;

    @OneToMany(mappedBy = "rater", cascade = CascadeType.ALL)
    private Set<Rating> ratings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Favorite> favorites;
}
