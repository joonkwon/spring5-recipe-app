package guru.springframework.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

	private NotesToNotesCommand notesToNotesCommand;
	private CategoryToCategoryCommand categoryToCategoryCommand;
	private IngredientToIngredientCommand ingredientToIngredientCommand;
	
	@Autowired
	public RecipeToRecipeCommand(NotesToNotesCommand noteToNotesCommand,
			CategoryToCategoryCommand categoryToCategoryCommand,
			IngredientToIngredientCommand ingredientToIngredientCommand) {
		this.notesToNotesCommand = noteToNotesCommand;
		this.categoryToCategoryCommand = categoryToCategoryCommand;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
	}

	@Override
	public RecipeCommand convert(Recipe source) {
		if (source == null) {
			return null;
		}
		
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(source.getId());
		recipeCommand.setDescription(source.getDescription());
		recipeCommand.setPrepTime(source.getPrepTime());
		recipeCommand.setCookTime(source.getCookTime());
		recipeCommand.setServings(source.getServings());
		recipeCommand.setSource(source.getSource());
		recipeCommand.setSource(source.getSource());
		recipeCommand.setUrl(source.getUrl());
		recipeCommand.setDirections(source.getDirections());
		recipeCommand.setDifficulty(source.getDifficulty());
		recipeCommand.setImage(source.getImage());
		recipeCommand.setNotes(notesToNotesCommand.convert(source.getNotes()));
		
		if (source.getIngredients() != null && source.getIngredients().size() > 0) {
			source.getIngredients()
				.forEach(ingredient -> recipeCommand.getIngredients().add(ingredientToIngredientCommand.convert(ingredient)));
		}
		
		if (source.getCategories() != null && source.getCategories().size() > 0) {
			source.getCategories()
				.forEach(category -> recipeCommand.getCategories().add(categoryToCategoryCommand.convert(category)));
		}
		
		return recipeCommand;
	}

}
