package com.project.cookshare.controllers;

import com.project.cookshare.DTOs.CommentDTO;
import com.project.cookshare.DTOs.IngredientDTO;
import com.project.cookshare.DTOs.CategoryDTO;
import com.project.cookshare.DTOs.InstructionStepDTO;
import com.project.cookshare.mapper.*;
import com.project.cookshare.models.*;
import com.project.cookshare.repositories.CategoryRepository;
import com.project.cookshare.repositories.CommentRepository;
import com.project.cookshare.repositories.IngredientRepository;
import com.project.cookshare.repositories.InstructionStepRepository;
import com.project.cookshare.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import com.project.cookshare.DTOs.RecipeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class RecipeController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final InstructionStepService instructionStepService;
    private final CategoryRepository categoryRepository;
    private final InstructionStepRepository instructionStepRepository;
    private final IngredientRepository ingredientRepository;
    private final CommentRepository commentRepository;
    private final RatingService ratingService;
    private final FavoriteService favoriteService;
    private final CommentService commentService;


    @Autowired
    public RecipeController(RecipeService recipeService, CategoryService categoryService, IngredientService ingredientService, InstructionStepService instructionStepService, CategoryRepository categoryRepository, InstructionStepRepository instructionStepRepository, IngredientRepository ingredientRepository, CommentRepository commentRepository, RatingService ratingService, FavoriteService favoriteService, CommentService commentService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.instructionStepService = instructionStepService;
        this.categoryRepository = categoryRepository;
        this.instructionStepRepository = instructionStepRepository;
        this.ingredientRepository = ingredientRepository;
        this.commentRepository = commentRepository;
        this.ratingService = ratingService;
        this.favoriteService = favoriteService;
        this.commentService = commentService;
    }

    @GetMapping("/recipes")
    public String listRecipes(Model model) {
        List<RecipeDTO> recipes = recipeService.getAllRecipes();
        Map<Integer, Integer> likesMap = new HashMap<>();
        Map<Integer, Integer> commentsMap = new HashMap<>();

        // Assuming you have a method in your service to get likes for all recipes
        for (RecipeDTO recipe : recipes) {
            Rating rating = ratingService.findRatingByRecipeId(recipe.getId());
            // Assuming that if there's no rating, the number of likes is 0
            int likes = (rating != null) ? rating.getLikes() : 0;
            likesMap.put(recipe.getId(), likes);
        }

        // Populate the comments map for each recipe
        for (RecipeDTO recipe : recipes) {
            long commentsCount = commentService.countCommentsByRecipeId(recipe.getId());
            int commentsInt = (int) commentsCount;
            commentsMap.put(recipe.getId(), commentsInt);
        }

        List<CategoryDTO> categories = categoryRepository.findAll().stream()
                .map(CategoryMapper::mapToCategoryDTO)
                .collect(Collectors.toList());

        model.addAttribute("recipes", recipes);
        model.addAttribute("likesMap", likesMap);
        model.addAttribute("commentsMap", commentsMap);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", "All Recipes");

        return "recipes";
    }

    @GetMapping("/recipe/{title}")
    public String getRecipeByName(@PathVariable("title") String title, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

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

        Rating rating = ratingService.findRatingByRecipeId(recipe.getId());
        int likes = (rating != null) ? rating.getLikes() : 0;
        int dislikes = (rating != null) ? rating.getDislikes() : 0;

        boolean isFavorite = false;
        if (user != null) {
            isFavorite = favoriteService.isFavorite(recipeDTO.getId(), user.getId()); // Ensure this method exists in your service
        }
        // Adding attributes to the model
        model.addAttribute("recipe", recipeDTO);
        model.addAttribute("instructionSteps", instructionSteps);
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("comments", comments);
        model.addAttribute("totalComments", comments.size());
        model.addAttribute("categories", categories); // Add categories to the model
        model.addAttribute("likes", likes);
        model.addAttribute("dislikes", dislikes);
        model.addAttribute("isFavorite", isFavorite); // Add whether the recipe is a favorite

        return "recipe";
    }


    @PostMapping("/recipe/{title}")
    public String handleRecipeActions(
            @PathVariable String title,
            @RequestParam(required = false) String commentText,
            @RequestParam(required = false) String action,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // User is not authenticated
            return "redirect:/login";
        }

        RecipeDTO recipeDTO = recipeService.findRecipeByName(title);
        if (recipeDTO == null) {
            redirectAttributes.addFlashAttribute("error", "Recipe not found");
            return "redirect:/recipes";
        }

        if ("comment".equals(action)) {
            if (commentText == null || commentText.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Comment cannot be empty");
                return "redirect:/recipe/" + title;
            }
            // Save comment logic
            Comment comment = new Comment();
            comment.setContent(commentText);
            comment.setRecipe(RecipeMapper.mapToRecipeEntity(recipeDTO));
            comment.setAuthor(user);
            comment.setPublishDate(new Date());
            commentService.addComment(comment);
            redirectAttributes.addFlashAttribute("success", "Comment added successfully");
        } else if ("like".equals(action) || "dislike".equals(action)) {
            // Like or dislike logic
            ratingService.updateRating(recipeDTO.getId(), action);
            redirectAttributes.addFlashAttribute("success", "Your feedback has been recorded");
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid action");
        }

        return "redirect:/recipe/" + title;
    }

    @PostMapping("/recipe/{title}/rating")
    @ResponseBody
    public ResponseEntity<Map<String, Integer>> handleRatingActions(
            @PathVariable String title,
            @RequestParam String action,
            HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        RecipeDTO recipeDTO = recipeService.findRecipeByName(title);
        if (recipeDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Rating updatedRating = ratingService.updateRating(recipeDTO.getId(), action);
        return ResponseEntity.ok(Map.of("likes", updatedRating.getLikes(), "dislikes", updatedRating.getDislikes()));
    }

    @PostMapping("/recipe/{title}/favorite")
    @ResponseBody
    public ResponseEntity<Map<String, Boolean>> handleFavoriteAction(
            @PathVariable String title,
            HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        RecipeDTO recipeDTO = recipeService.findRecipeByName(title);
        if (recipeDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        boolean isFavorite = favoriteService.addFavorite(recipeDTO.getId(), user.getId());
        return ResponseEntity.ok(Map.of("isFavorite", isFavorite));
    }



    @GetMapping("/recipes/{category}")
    public String listRecipesByCategory(@PathVariable("category") String categoryName, Model model) {

        List<RecipeDTO> recipes = recipeService.getAllRecipes();
        List<RecipeDTO> recipesByCategory = recipeService.getRecipesByCategory(categoryName);

        Map<Integer, Integer> likesMap = new HashMap<>();
        Map<Integer, Integer> commentsMap = new HashMap<>();

        // Assuming you have a method in your service to get likes for all recipes
        for (RecipeDTO recipe : recipes) {
            Rating rating = ratingService.findRatingByRecipeId(recipe.getId());
            // Assuming that if there's no rating, the number of likes is 0
            int likes = (rating != null) ? rating.getLikes() : 0;
            likesMap.put(recipe.getId(), likes);
        }

        // Populate the comments map for each recipe
        for (RecipeDTO recipe : recipes) {
            long commentsCount = commentService.countCommentsByRecipeId(recipe.getId());
            int commentsInt = (int) commentsCount;
            commentsMap.put(recipe.getId(), commentsInt);
        }

        List<CategoryDTO> categories = categoryRepository.findAll().stream()
                .map(CategoryMapper::mapToCategoryDTO)
                .collect(Collectors.toList());

        model.addAttribute("recipes", recipesByCategory);
        model.addAttribute("categories", categories);
        model.addAttribute("likesMap", likesMap);
        model.addAttribute("commentsMap", commentsMap);
        model.addAttribute("selectedCategory", categoryName);

        return "recipes";
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

    @PostMapping("/submitRecipe")
    public String processRecipeForm(@ModelAttribute("recipe") Recipe recipe, BindingResult result, HttpServletRequest request, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "submit_recipe"; // Redirect back to form if there are errors
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Integer userId = null;
        if (user != null) {
            userId = user.getId(); // Get the user ID from the user object
        } else {
            attributes.addFlashAttribute("error", "You must be logged in to submit a recipe.");
            return "redirect:/login";
        }

        // Extracting manually added ingredients and instruction steps from request
        String[] ingredientNames = request.getParameterValues("ingredientNames");
        String[] ingredientQuantities = request.getParameterValues("ingredientQuantities");
        String[] instructionSteps = request.getParameterValues("instructionSteps");

        // Logic to add ingredients and instruction steps to recipe object
        if (ingredientNames != null) {
            for (int i = 0; i < ingredientNames.length; i++) {
                if (ingredientNames[i] != null && !ingredientNames[i].isEmpty() && ingredientQuantities[i] != null) {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setName(ingredientNames[i]);
                    ingredient.setQuantity(ingredientQuantities[i]);
                    recipe.addIngredient(ingredient);
                }
            }
        }

        if (instructionSteps != null && instructionSteps.length > 0) {
            for (int i = instructionSteps.length - 1; i >= 0; i--) { // Loop in reverse order
                if (!instructionSteps[i].isEmpty()) {
                    InstructionStep instructionStep = new InstructionStep();
                    instructionStep.setInstruction(instructionSteps[i]);
                    // Set the step number so that it reflects the original order
                    instructionStep.setStep_number(instructionSteps.length - i);
                    recipe.addInstructionStep(instructionStep); // Associate instruction with recipe
                }
            }
        }

        /**
         * length = 3
         * loop1 : i = 2 .
         */

        recipe.setCreated_date(new Date());

        // Use the service to add the recipe, associated with the obtained user ID
        recipeService.addRecipe(recipe, userId);

        // Add success message and redirect to avoid duplicate submissions
        attributes.addFlashAttribute("message", "Recipe successfully added!");
        return "redirect:/recipes";
    }

    @GetMapping("/editRecipe/{id}")
    public String showEditRecipeForm(@PathVariable("id") Integer id, Model model) {
        // Fetch the recipe with its details. Assuming recipeService handles fetching by id including ingredients and instructions.
        Recipe recipe = recipeService.findRecipeById(id);

        // Assuming you have methods in your service to fetch ingredients and instruction steps associated with the recipe
        List<Ingredient> ingredients = ingredientService.findByRecipeId(recipe.getId());
        List<InstructionStep> instructionSteps = instructionStepService.findByRecipeId(recipe.getId());
        List<CategoryDTO> categories = categoryRepository.findAll().stream()
                .map(CategoryMapper::mapToCategoryDTO) // Assuming you have this mapper method
                .collect(Collectors.toList());

        // Adding attributes to the model to be returned to the view
        model.addAttribute("recipe", recipe);
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("instructionSteps", instructionSteps);
        model.addAttribute("categories", categories);

        return "edit_recipe"; // Name of your Thymeleaf template for editing recipes
    }
    @PostMapping("/editRecipe")
    public String processEditRecipeForm(@ModelAttribute("recipe") Recipe updatedRecipe, BindingResult result, HttpServletRequest request, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "edit_recipe"; // Return back to the form if there are errors
        }

        // Assuming we have a session or a way to get the currently logged in user
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            attributes.addFlashAttribute("error", "You must be logged in to edit a recipe.");
            return "redirect:/login";
        }

        // Fetch the existing recipe from the database
        Recipe existingRecipe = recipeService.findRecipeById(updatedRecipe.getId());
        // Update existing recipe with new details
        existingRecipe.setTitle(updatedRecipe.getTitle());
        existingRecipe.setDescription(updatedRecipe.getDescription());
        existingRecipe.setCooking_time(updatedRecipe.getCooking_time());
        existingRecipe.setImage(updatedRecipe.getImage());
        existingRecipe.setCategory(updatedRecipe.getCategory());  // Assuming you manage category similarly

        // Extract manually added ingredients and instruction steps from the request
        String[] ingredientNames = request.getParameterValues("ingredientNames");
        String[] ingredientQuantities = request.getParameterValues("ingredientQuantities");
        String[] instructionSteps = request.getParameterValues("instructionSteps");

        // Clear previous ingredients and steps to replace with the new ones
        existingRecipe.getIngredients().clear();

        if (ingredientNames != null) {
            for (int i = 0; i < ingredientNames.length; i++) {
                if (!ingredientNames[i].isEmpty() && !ingredientQuantities[i].isEmpty()) {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setName(ingredientNames[i]);
                    ingredient.setQuantity(ingredientQuantities[i]);
                    ingredient.setRecipe(existingRecipe); // Set the recipe to each ingredient
                    existingRecipe.getIngredients().add(ingredient); // Add ingredient to the recipe
                }
            }
        }

        existingRecipe.getInstruction_step().clear();

        existingRecipe.getIngredients().clear();

        if (ingredientNames != null) {
            for (int i = 0; i < ingredientNames.length; i++) {
                if (!ingredientNames[i].isEmpty() && !ingredientQuantities[i].isEmpty()) {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setName(ingredientNames[i]);
                    ingredient.setQuantity(ingredientQuantities[i]);
                    ingredient.setRecipe(existingRecipe); // Set the recipe to each ingredient
                    existingRecipe.getIngredients().add(ingredient); // Add ingredient to the recipe
                }
            }
        }
// Clear previous instruction steps to replace with the new ones
        existingRecipe.getInstruction_step().clear(); // Ensure this is the correct method name, it should match your entity structure

// Re-add the instruction steps from the form
        if (instructionSteps != null) {
            int stepNumber = 1;
            for (String step : instructionSteps) {
                if (!step.isEmpty()) {
                    InstructionStep instructionStep = new InstructionStep();
                    instructionStep.setInstruction(step);
                    instructionStep.setStep_number(stepNumber++); // Ensure your method names match the actual methods in your InstructionStep class
                    instructionStep.setRecipe(existingRecipe); // Associate the instruction step with the current recipe
                    // This ensures that the instruction steps are linked to the correct recipe
                    existingRecipe.getInstruction_step().add(instructionStep); // Re-add the instruction step to the existing recipe
                }
            }
        }

// After this, when you save your existingRecipe, the changes should be reflected correctly



        // Save the updated recipe
        recipeService.saveRecipe(existingRecipe, user.getId()); // Ensure your service method correctly updates the recipe

        attributes.addFlashAttribute("message", "Recipe successfully updated!");
        return "redirect:/profile"; // Redirect to a confirmation or profile page
    }

}
