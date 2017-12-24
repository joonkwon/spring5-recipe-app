package guru.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

	private NotesCommandToNotes ncToN;
	private CategoryCommandToCategory ccToC;
	private IngredientCommandToIngredient icToI;
	
	public RecipeCommandToRecipe(NotesCommandToNotes ncToN, CategoryCommandToCategory ccToC,
			IngredientCommandToIngredient icToI) {
		this.ncToN = ncToN;
		this.ccToC = ccToC;
		this.icToI = icToI;
	}
	@Override
	public Recipe convert(RecipeCommand source) {
		if (source == null) {
			return null;
		}
		
		Recipe recipe = new Recipe();
		recipe.setId(source.getId());
		recipe.setDescription(source.getDescription());
		recipe.setPrepTime(source.getPrepTime());
		recipe.setCookTime(source.getCookTime());
		recipe.setServings(source.getServings());
		recipe.setSource(source.getSource());
		recipe.setSource(source.getSource());
		recipe.setUrl(source.getUrl());
		recipe.setDirections(source.getDirections());
		recipe.setDifficulty(source.getDifficulty());
		recipe.setImage(source.getImage());
		recipe.setNotes(ncToN.convert(source.getNotes()));
		
		if (source.getIngredients() != null && source.getIngredients().size() > 0) {
			source.getIngredients()
				.forEach(ingredient -> recipe.getIngredients().add(icToI.convert(ingredient)));
		}
		
		if (source.getCategories() != null && source.getCategories().size() > 0) {
			source.getCategories()
				.forEach(category -> recipe.getCategories().add(ccToC.convert(category)));
		}
		
		return recipe;
	}

}
