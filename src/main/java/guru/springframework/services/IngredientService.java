package guru.springframework.services;

import java.util.Set;

import guru.springframework.commands.IngredientCommand;

public interface IngredientService {
	IngredientCommand getCommandByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
	Set<IngredientCommand> getIngredientCommandsByrecipeId(Long recipeId);
}
