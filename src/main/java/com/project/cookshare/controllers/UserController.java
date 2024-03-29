package com.project.cookshare.controllers;

import com.project.cookshare.DTOs.CategoryDTO;
import com.project.cookshare.DTOs.RecipeDTO;
import com.project.cookshare.DTOs.UserDTO;
import com.project.cookshare.mapper.CategoryMapper;
import com.project.cookshare.mapper.UserMapper;
import com.project.cookshare.models.*;
import com.project.cookshare.repositories.CategoryRepository;
import com.project.cookshare.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.project.cookshare.DTOs.CategoryDTO;
import com.project.cookshare.DTOs.RecipeDTO;
import com.project.cookshare.DTOs.UserDTO;
import com.project.cookshare.mapper.CategoryMapper;
import com.project.cookshare.mapper.UserMapper;
import com.project.cookshare.models.*;
import com.project.cookshare.repositories.CategoryRepository;
import com.project.cookshare.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class UserController {
    private final UserService userService;
    private final RecipeService recipeService;
    private final RatingService ratingService;
    private final FavoriteService favoriteService;
    private final IngredientService ingredientService;
    private final InstructionStepService instructionStepService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public UserController(UserService userService, RecipeService recipeService, RatingService ratingService, FavoriteService favoriteService, IngredientService ingredientService, InstructionStepService instructionStepService, CategoryRepository categoryRepository) {
        this.userService = userService;
        this.recipeService = recipeService;
        this.ratingService = ratingService;
        this.favoriteService = favoriteService;
        this.ingredientService = ingredientService;
        this.instructionStepService = instructionStepService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model, HttpSession session) {
        User existingUser = userService.findUserByUsername(user.getUsername());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            session.setAttribute("user", existingUser);
            return "redirect:/recipes";
        } else {
            model.addAttribute("loginError", "Invalid username or password.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logoutUser(SessionStatus status, HttpSession session) {
        status.setComplete();
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profile( HttpSession session, Model model) {
        List<RecipeDTO> recipes = recipeService.getAllRecipes();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        Map<Integer, Integer> likesMap = new HashMap<>();
        List<RecipeDTO> favoriteRecipes = recipeService.getFavoriteRecipesByUser(user.getId());

        for (RecipeDTO recipe : recipes) {
            Rating rating = ratingService.findRatingByRecipeId(recipe.getId());
            int likes = (rating != null) ? rating.getLikes() : 0;
            likesMap.put(recipe.getId(), likes);
        }

        int recipesSubmittedCount = userService.calculateRecipesSubmittedByUser(user.getId());
        User userDTO = userService.findUserByUsername(user.getUsername());
        userDTO.setRecipesSubmitted(recipesSubmittedCount);
        model.addAttribute("userDTO", userDTO);
        recipes = recipeService.getRecipesByAuthor(user);

        model.addAttribute("user", user);
        model.addAttribute("recipes", recipes);
        model.addAttribute("favoriteRecipes", favoriteRecipes);
        model.addAttribute("likesMap", likesMap);
        return "my_profile";
    }

    @PostMapping("/deleteRecipe/{id}")
    public String deleteRecipe(@PathVariable("id") int recipeId, HttpSession session, RedirectAttributes attributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            attributes.addFlashAttribute("error", "You must be logged in to delete recipes.");
            return "redirect:/login";
        }

        Recipe recipe = recipeService.findRecipeById(recipeId);
        if (recipe == null || !recipe.getAuthor().getId().equals(user.getId())) {
            attributes.addFlashAttribute("error", "You do not have permission to delete this recipe.");
            return "redirect:/profile";
        }

        recipeService.deleteRecipe(recipeId);

        attributes.addFlashAttribute("message", "Recipe successfully deleted.");
        return "redirect:/profile";
    }

    @PostMapping("/removeFavorite/{id}")
    public String removeRecipeFromFavorites(@PathVariable("id") int recipeId, HttpSession session, RedirectAttributes attributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            attributes.addFlashAttribute("error", "You must be logged in to remove favorites.");
            return "redirect:/login";
        }

        favoriteService.removeFavorite(recipeId, user.getId());

        attributes.addFlashAttribute("message", "Recipe successfully removed from favorites.");
        return "redirect:/profile";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserDTO userDTO, Model model, HttpSession session) {
            userService.registerUser(userDTO);
            session.setAttribute("user", UserMapper.mapToUserEntity(userDTO));
            return "login";
        }

    @PostMapping("/updateProfile")
    public String updateProfile(
            @RequestParam("name") String name,
            @RequestParam("username") String username,
            @RequestParam(value = "password", required = false) String password,
            HttpSession session,
            RedirectAttributes attributes) {

        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            attributes.addFlashAttribute("error", "You need to log in to update your profile.");
            return "redirect:/login";
        }

        User user = new User();
        user.setId(currentUser.getId());
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);

        try {
            User updatedUser = userService.updateProfile(user);
            session.setAttribute("user", updatedUser);
            attributes.addFlashAttribute("message", "Profile successfully updated.");
        } catch (Exception e) {
            attributes.addFlashAttribute("error", "Profile update failed: " + e.getMessage());
        }

        return "redirect:/profile";
    }

    @PostMapping("/editRecipe")
    public String processEditRecipeForm(@ModelAttribute("recipe") Recipe updatedRecipe, BindingResult result, HttpServletRequest request, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "edit_recipe";
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            attributes.addFlashAttribute("error", "You must be logged in to edit a recipe.");
            return "redirect:/login";
        }

        Recipe existingRecipe = recipeService.findRecipeById(updatedRecipe.getId());
        existingRecipe.setTitle(updatedRecipe.getTitle());
        existingRecipe.setDescription(updatedRecipe.getDescription());
        existingRecipe.setCooking_time(updatedRecipe.getCooking_time());
        existingRecipe.setImage(updatedRecipe.getImage());
        existingRecipe.setCategory(updatedRecipe.getCategory());

        String[] ingredientNames = request.getParameterValues("ingredientNames");
        String[] ingredientQuantities = request.getParameterValues("ingredientQuantities");
        String[] instructionSteps = request.getParameterValues("instructionSteps");

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
                    ingredient.setRecipe(existingRecipe);
                    existingRecipe.getIngredients().add(ingredient);
                }
            }
        }
        existingRecipe.getInstruction_step().clear();
        if (instructionSteps != null) {
            int stepNumber = 1;
            for (String step : instructionSteps) {
                if (!step.isEmpty()) {
                    InstructionStep instructionStep = new InstructionStep();
                    instructionStep.setInstruction(step);
                    instructionStep.setStep_number(stepNumber++);
                    instructionStep.setRecipe(existingRecipe);
                    existingRecipe.getInstruction_step().add(instructionStep);
                }
            }
        }

        recipeService.saveRecipe(existingRecipe, user.getId());

        return "redirect:/profile";
    }

    @PostMapping("/submitRecipe")
    public String processRecipeForm(@ModelAttribute("recipe") Recipe recipe, BindingResult result, HttpServletRequest request, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "submit_recipe";
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Integer userId;
        if (user != null) {
            userId = user.getId();
        } else {
            attributes.addFlashAttribute("error", "You must be logged in to submit a recipe.");
            return "redirect:/login";
        }

        String[] ingredientNames = request.getParameterValues("ingredientNames");
        String[] ingredientQuantities = request.getParameterValues("ingredientQuantities");
        String[] instructionSteps = request.getParameterValues("instructionSteps");

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
        recipe.setCreated_date(new Date());

        recipeService.addRecipe(recipe, userId);

        return "redirect:/recipes";
    }

    @GetMapping("/editRecipe/{id}")
    public String showEditRecipeForm(@PathVariable("id") Integer id, Model model,HttpServletRequest request) {
        Recipe recipe = recipeService.findRecipeById(id);
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        List<Ingredient> ingredients = ingredientService.findByRecipeId(recipe.getId());
        List<InstructionStep> instructionSteps = instructionStepService.findByRecipeId(recipe.getId());
        List<CategoryDTO> categories = categoryRepository.findAll().stream()
                .map(CategoryMapper::mapToCategoryDTO)
                .collect(Collectors.toList());

        model.addAttribute("recipe", recipe);
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("instructionSteps", instructionSteps);
        model.addAttribute("categories", categories);

        return "edit_recipe";
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

}
