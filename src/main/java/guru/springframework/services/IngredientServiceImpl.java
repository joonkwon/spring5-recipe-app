package guru.springframework.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
	
	private RecipeService recipeService;
	
	@Autowired
	public IngredientServiceImpl(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@Override
	public IngredientCommand getCommandByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		RecipeCommand recipeCommand = recipeService.getCommandById(recipeId);
		if (recipeCommand != null) {
			Set<IngredientCommand> ingredientCommands = recipeCommand.getIngredients();
			for (IngredientCommand inCom : ingredientCommands) {
				if (ingredientId.equals(inCom.getId())) {
					return inCom;
				} 	
			}
		}
		log.debug("No ingredient with recipe ID: " + recipeId + 
				  ", ingredient ID: "+ ingredientId);
		return null;
	}

	@Override
	public Set<IngredientCommand> getIngredientCommandsByrecipeId(Long recipeId) {
		log.debug("Fetching recipe, ID: " + recipeId);
		RecipeCommand recipeCommand = recipeService.getCommandById(recipeId);
		
		if (recipeCommand == null) {
			log.debug("Recipe not found, ID: " + recipeId);
			return null;
		}
		
		Set<IngredientCommand> ingredientCommands = recipeCommand.getIngredients();
		
		if (ingredientCommands == null) {
			log.debug("No ingredient found for recipe ID: " + recipeId);
			return null;
		}
		
		return ingredientCommands;
	}

	

}
