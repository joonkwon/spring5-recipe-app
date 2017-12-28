package guru.springframework.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {
	
	private final IngredientService ingredientService;
	private final UnitOfMeasureService uomService;
	
	@Autowired
	public IngredientController(IngredientService ingredientService, UnitOfMeasureService uomService) {
		this.ingredientService = ingredientService;
		this.uomService = uomService;
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
	@RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
	public String updateIngredient(Model model, 
			@PathVariable("recipeId") String recipeId,
			@PathVariable("ingredientId") String ingredientId) {
		log.debug("Fetching ingredient, ID: " + ingredientId);
		IngredientCommand ingredientCommand = ingredientService.getCommandByRecipeIdAndIngredientId(new Long(recipeId), new Long(ingredientId));
		model.addAttribute("ingredient", ingredientCommand);
		
		log.debug("Fetching all unit of measure");
		model.addAttribute("uomList", uomService.getAllUomCommand());
		
		return "recipe/ingredient/ingredientform";
	}
	
	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
	public String deleteIngredient(@PathVariable("recipeId") String recipeId,
			@PathVariable("ingredientId") String ingredientId) throws Exception {

		ingredientService.deleteById(new Long(recipeId), new Long(ingredientId));
		
		return "redirect:/recipe/" + recipeId + "/ingredients";
	}
	
	@PostMapping
	@RequestMapping("/recipe/{recipeId}/ingredient")
	public String saveIngredient(@ModelAttribute IngredientCommand ingredient, @PathVariable("recipeId") Long recipeId) {
		log.debug("saving ingredient for recipe");
		IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredient, recipeId);
		
		log.debug("ingredient (id: " + savedIngredientCommand.getId() 
				+ "successfully saved for recipe (id: " 
				+ savedIngredientCommand.getRecipe().getId());
		return "redirect:/recipe/"+ recipeId + 
				"/ingredient/" + ingredient.getId() + "/show";
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
