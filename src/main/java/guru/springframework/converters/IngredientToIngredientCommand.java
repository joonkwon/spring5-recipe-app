package guru.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Ingredient;
import lombok.Synchronized;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

	private UnitOfMeasureToUnitOfMeasureCommand uomToUomCommand;	
	
	public IngredientToIngredientCommand() {
	}

	@Synchronized
	@Nullable
	@Override
	public IngredientCommand convert(Ingredient source) {
		if (source == null) {
			return null;
		}
		
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(source.getId());
		ingredientCommand.setDescription(source.getDescription());
		
		RecipeCommand recipeCommand = new RecipeCommand();
		if (source.getRecipe() != null) {
			recipeCommand.setId(source.getRecipe().getId());
		}
		ingredientCommand.setRecipe(recipeCommand);
		
		ingredientCommand.setAmount(source.getAmount());
		uomToUomCommand = new UnitOfMeasureToUnitOfMeasureCommand();
		ingredientCommand.setUom(uomToUomCommand.convert(source.getUom()));
		
		return ingredientCommand;
	}

}
