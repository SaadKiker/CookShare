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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class RecipeController {
    private final RecipeService recipeService;
    private final CategoryRepository categoryRepository;
    private final InstructionStepRepository instructionStepRepository;
    private final IngredientRepository ingredientRepository;
    private final CommentRepository commentRepository;
    private final RatingService ratingService;
    private final FavoriteService favoriteService;
    private final CommentService commentService;

    @Autowired
    public RecipeController(RecipeService recipeService, CategoryRepository categoryRepository, InstructionStepRepository instructionStepRepository, IngredientRepository ingredientRepository, CommentRepository commentRepository, RatingService ratingService, FavoriteService favoriteService, CommentService commentService) {
        this.recipeService = recipeService;
        this.categoryRepository = categoryRepository;
        this.instructionStepRepository = instructionStepRepository;
        this.ingredientRepository = ingredientRepository;
        this.commentRepository = commentRepository;
        this.ratingService = ratingService;
        this.favoriteService = favoriteService;
        this.commentService = commentService;
    }

    @GetMapping("/recipes")
    public String listRecipes( HttpServletRequest request, Model model) {
        List<RecipeDTO> recipes = recipeService.getAllRecipes();
        Map<Integer, Integer> likesMap = new HashMap<>();
        Map<Integer, Integer> commentsMap = new HashMap<>();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        for (RecipeDTO recipe : recipes) {
            Rating rating = ratingService.findRatingByRecipeId(recipe.getId());
            int likes = (rating != null) ? rating.getLikes() : 0;
            likesMap.put(recipe.getId(), likes);
        }

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
        if (user == null) {
            return "redirect:/login";
        }
        RecipeDTO recipeDTO = recipeService.findRecipeByName(title);
        Recipe recipe = RecipeMapper.mapToRecipeEntity(recipeDTO);

        List<InstructionStepDTO> instructionSteps = instructionStepRepository.findByRecipe(recipe).stream()
                .map(InstructionStepMapper::mapToInstructionStepDTO)
                .collect(Collectors.toList());

        List<IngredientDTO> ingredients = ingredientRepository.findByRecipe(recipe).stream()
                .map(IngredientMapper::mapToIngredientDTO)
                .collect(Collectors.toList());

        List<CommentDTO> comments = commentRepository.findByRecipe(recipe).stream()
                .map(CommentMapper::mapToCommentDTO)
                .toList();

        List<CategoryDTO> categories = categoryRepository.findAll().stream()
                .map(CategoryMapper::mapToCategoryDTO)
                .collect(Collectors.toList());

        Rating rating = ratingService.findRatingByRecipeId(recipe.getId());
        int likes = (rating != null) ? rating.getLikes() : 0;
        int dislikes = (rating != null) ? rating.getDislikes() : 0;

        boolean isFavorite = false;
        if (user != null) {
            isFavorite = favoriteService.isFavorite(recipeDTO.getId(), user.getId());
        }

        model.addAttribute("recipe", recipeDTO);
        model.addAttribute("instructionSteps", instructionSteps);
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("comments", comments);
        model.addAttribute("totalComments", comments.size());
        model.addAttribute("categories", categories);
        model.addAttribute("likes", likes);
        model.addAttribute("dislikes", dislikes);
        model.addAttribute("isFavorite", isFavorite);

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

            Comment comment = new Comment();
            comment.setContent(commentText);
            comment.setRecipe(RecipeMapper.mapToRecipeEntity(recipeDTO));
            comment.setAuthor(user);
            comment.setPublishDate(new Date());
            commentService.addComment(comment);
            redirectAttributes.addFlashAttribute("success", "Comment added successfully");
        } else if ("like".equals(action) || "dislike".equals(action)) {
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
            HttpServletRequest request) throws UnsupportedEncodingException {
        String decodedTitle = URLDecoder.decode(title, StandardCharsets.UTF_8.name());

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        RecipeDTO recipeDTO = recipeService.findRecipeByName(decodedTitle);
        if (recipeDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        boolean isFavorite = favoriteService.addFavorite(recipeDTO.getId(), user.getId());
        return ResponseEntity.ok(Map.of("isFavorite", isFavorite));
    }

    @GetMapping("/recipes/{category}")
    public String listRecipesByCategory(@PathVariable("category") String categoryName, Model model,  HttpServletRequest request) {

        List<RecipeDTO> recipes = recipeService.getAllRecipes();
        List<RecipeDTO> recipesByCategory = recipeService.getRecipesByCategory(categoryName);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        Map<Integer, Integer> likesMap = new HashMap<>();
        Map<Integer, Integer> commentsMap = new HashMap<>();

        for (RecipeDTO recipe : recipes) {
            Rating rating = ratingService.findRatingByRecipeId(recipe.getId());
            int likes = (rating != null) ? rating.getLikes() : 0;
            likesMap.put(recipe.getId(), likes);
        }

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
    public String submitRecipeForm( HttpServletRequest request,Model model) {
        Recipe recipe = new Recipe();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        List<CategoryDTO> categories = categoryRepository.findAll().stream()
                .map(CategoryMapper::mapToCategoryDTO) // Assuming you have this mapper method
                .collect(Collectors.toList());

        model.addAttribute("categories", categories);
        model.addAttribute("recipe", recipe);
        return "submit_recipe";
    }

    @PostMapping("/recipes/search")
    public String searchRecipes(@RequestParam String searchQuery, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<RecipeDTO> filteredRecipes = recipeService.searchByTitle(searchQuery);
        Map<Integer, Integer> likesMap = new HashMap<>();
        Map<Integer, Integer> commentsMap = new HashMap<>();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        for (RecipeDTO recipe : filteredRecipes) {
            Rating rating = ratingService.findRatingByRecipeId(recipe.getId());
            int likes = (rating != null) ? rating.getLikes() : 0;
            likesMap.put(recipe.getId(), likes);
        }

        for (RecipeDTO recipe : filteredRecipes) {
            long commentsCount = commentService.countCommentsByRecipeId(recipe.getId());
            int commentsInt = (int) commentsCount;
            commentsMap.put(recipe.getId(), commentsInt);
        }

        List<CategoryDTO> categories = categoryRepository.findAll().stream()
                .map(CategoryMapper::mapToCategoryDTO)
                .collect(Collectors.toList());

        model.addAttribute("recipes", filteredRecipes);
        model.addAttribute("likesMap", likesMap);
        model.addAttribute("commentsMap", commentsMap);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", "All Recipes");

        return "recipes";
    }

}
