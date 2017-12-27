package guru.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {
	
	private UnitOfMeasureCommandToUnitOfMeasure uomCommandToUom;
	
	public IngredientCommandToIngredient() {
	}

	@Synchronized
	@Nullable
	@Override
	public Ingredient convert(IngredientCommand source) {
		if (source == null) {
			return null;
		}
		final Ingredient ingredient = new Ingredient();
		ingredient.setId(source.getId());
		ingredient.setDescription(source.getDescription());
		
		Recipe recipe = new Recipe();
		if (source.getRecipe() != null) {
			recipe.setId(source.getRecipe().getId());
		}
		ingredient.setRecipe(recipe);
		
		ingredient.setAmount(source.getAmount());
		uomCommandToUom = new UnitOfMeasureCommandToUnitOfMeasure();
		ingredient.setUom(uomCommandToUom.convert(source.getUom()));
		
		return ingredient;
	}

}