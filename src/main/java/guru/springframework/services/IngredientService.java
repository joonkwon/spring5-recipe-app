package guru.springframework.services;

import java.util.Set;

import guru.springframework.commands.IngredientCommand;

public interface IngredientService {
	IngredientCommand getCommandByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
	Set<IngredientCommand> getIngredientCommandsByrecipeId(Long recipeId);
	IngredientCommand saveIngredientCommand(IngredientCommand command, Long recipeId);
	void deleteById(Long recipeId, Long ingredientId);
}
