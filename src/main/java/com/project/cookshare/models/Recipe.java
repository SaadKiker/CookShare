package com.project.cookshare.models;

import lombok.*;
import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "recipe")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_date;

    @Column
    private String cooking_time;

    @Column
    private String image;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Set<InstructionStep> instruction_step = new HashSet<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Set<Rating> ratings;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Set<Favorite> favorites;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public void addIngredient(Ingredient ingredient) {
        if (this.ingredients == null) {
            this.ingredients = new HashSet<>();
        }
        ingredients.add(ingredient);
        ingredient.setRecipe(this);
    }

    public void addInstructionStep(InstructionStep instructionStep) {
        if (this.instruction_step == null) {
            this.instruction_step = new HashSet<>();
        }
        instruction_step.add(instructionStep);
        instructionStep.setRecipe(this);
    }

}
