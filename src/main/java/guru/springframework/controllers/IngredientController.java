package guru.springframework.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.services.IngredientService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {
	
	private final IngredientService ingredientService;
	
	@Autowired
	public IngredientController(IngredientService ingredientService) {
		this.ingredientService = ingredientService;
	}
	
	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
	public String showIngredient(Model model, 
			@PathVariable("recipeId") String recipeId,
			@PathVariable("ingredientId") String ingredientId) {
		log.debug("Fetching ingredient, ID: " + ingredientId);
		IngredientCommand ingredientCommand = ingredientService.getCommandByRecipeIdAndIngredientId(new Long(recipeId), new Long(ingredientId));
		model.addAttribute("ingredient", ingredientCommand);
		return "recipe/ingredient/show";
	}

	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredients")
	public String showIngredientsList(@PathVariable("recipeId") String recipeId, Model model) {
		log.debug("Fetching all ingredeints for recipe ID: " + recipeId);
		Set<IngredientCommand> ingredients = ingredientService.getIngredientCommandsByrecipeId(new Long(recipeId));
		model.addAttribute("ingredients", ingredients);
		return "recipe/ingredient/list";
	}
}
