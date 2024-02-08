package com.project.cookshare.controllers;

import com.project.cookshare.DTOs.CommentDTO;
import com.project.cookshare.DTOs.IngredientDTO;
import com.project.cookshare.DTOs.CategoryDTO;
import com.project.cookshare.DTOs.InstructionStepDTO;
import com.project.cookshare.mapper.*;
import com.project.cookshare.models.Recipe;
import com.project.cookshare.repositories.CategoryRepository;
import com.project.cookshare.repositories.CommentRepository;
import com.project.cookshare.repositories.IngredientRepository;
import com.project.cookshare.repositories.InstructionStepRepository;
import org.springframework.ui.Model;
import com.project.cookshare.DTOs.RecipeDTO;
import com.project.cookshare.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RecipeController {
    private final RecipeService recipeService;
    private final CategoryRepository categoryRepository;
    private final InstructionStepRepository instructionStepRepository;
    private final IngredientRepository ingredientRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public RecipeController(RecipeService recipeService, CategoryRepository categoryRepository, InstructionStepRepository instructionStepRepository, IngredientRepository ingredientRepository, CommentRepository commentRepository) {
        this.recipeService = recipeService;
        this.categoryRepository = categoryRepository;
        this.instructionStepRepository = instructionStepRepository;
        this.ingredientRepository = ingredientRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/recipes")
    public String listRecipes(Model model) {
        List<RecipeDTO> recipes = recipeService.getAllRecipes();

        List<CategoryDTO> categories = categoryRepository.findAll().stream()
                .map(CategoryMapper::mapToCategoryDTO) // Assuming you have this mapper method
                .collect(Collectors.toList());

        model.addAttribute("recipes", recipes);
        model.addAttribute("categories", categories); // Add categories to the model

        return "recipes";
    }

    @GetMapping("/recipe/{title}")
    public String getRecipeByName(@PathVariable("title") String title, Model model) {
        RecipeDTO recipeDTO = recipeService.findRecipeByName(title);
        Recipe recipe = RecipeMapper.mapToRecipeEntity(recipeDTO);

        // Fetching instruction steps
        List<InstructionStepDTO> instructionSteps = instructionStepRepository.findByRecipe(recipe).stream()
                .map(InstructionStepMapper::mapToInstructionStepDTO)
                .collect(Collectors.toList());

        // Fetching ingredients for the recipe
        List<IngredientDTO> ingredients = ingredientRepository.findByRecipe(recipe).stream()
                .map(IngredientMapper::mapToIngredientDTO)
                .collect(Collectors.toList());

        // Fetching comments for the recipe
        List<CommentDTO> comments = commentRepository.findByRecipe(recipe).stream()
                .map(CommentMapper::mapToCommentDTO)
                .toList();

        // Fetching categories for the recipe
        // This assumes you have a method in a repository or service to fetch categories by recipe
        List<CategoryDTO> categories = categoryRepository.findAll().stream()
                .map(CategoryMapper::mapToCategoryDTO) // Assuming you have this mapper method
                .collect(Collectors.toList());

        // Adding attributes to the model
        model.addAttribute("recipe", recipeDTO);
        model.addAttribute("instructionSteps", instructionSteps);
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("comments", comments);
        model.addAttribute("totalComments", comments.size());
        model.addAttribute("categories", categories); // Add categories to the model

        return "recipe";
    }

    @GetMapping("/recipes/{category}")
    public String listRecipesByCategory(@PathVariable("category") String categoryName, Model model) {
        // Assuming you have a method in your service to fetch recipes by category name
        // This method needs to be implemented in your service layer
        List<RecipeDTO> recipesByCategory = recipeService.getRecipesByCategory(categoryName);

        // Fetch all categories to display in the sidebar or category list
        List<CategoryDTO> categories = categoryRepository.findAll().stream()
                .map(CategoryMapper::mapToCategoryDTO) // Assuming you have this mapper method
                .collect(Collectors.toList());

        // Add filtered recipes and all categories to the model
        model.addAttribute("recipes", recipesByCategory);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", categoryName); // Optionally mark the selected category for highlighting in UI

        return "recipes"; // Assuming "recipes" is the name of your template for listing recipes
    }

    @GetMapping("/submit_recipe")
    public String submitRecipeForm(Model model) {
        Recipe recipe = new Recipe();

        List<CategoryDTO> categories = categoryRepository.findAll().stream()
                .map(CategoryMapper::mapToCategoryDTO) // Assuming you have this mapper method
                .collect(Collectors.toList());

        model.addAttribute("categories", categories);
        model.addAttribute("recipe", recipe);
        return "submit_recipe";
    }

    @GetMapping("/find_recipe")
    public String findRecipeForm(Model model) {
        List<RecipeDTO> recipes = recipeService.getAllRecipes();

        List<CategoryDTO> categories = categoryRepository.findAll().stream()
                .map(CategoryMapper::mapToCategoryDTO) // Assuming you have this mapper method
                .collect(Collectors.toList());

        model.addAttribute("recipes", recipes);
        model.addAttribute("categories", categories); // Add categories to the model

        return "find_recipe";
    }

}
