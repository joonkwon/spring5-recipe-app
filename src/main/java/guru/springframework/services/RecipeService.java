package guru.springframework.services;

import java.util.Set;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;

public interface RecipeService {

	Set<Recipe> getRecipes();
	Recipe getRecipeById(Long id);
	RecipeCommand getCommandById(Long id);
	RecipeCommand saveRecipeCommand(RecipeCommand command);
	void deleteById(Long id);
}
