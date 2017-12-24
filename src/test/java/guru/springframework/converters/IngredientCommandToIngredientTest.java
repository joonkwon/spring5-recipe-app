package guru.springframework.converters;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;

public class IngredientCommandToIngredientTest {

	public static final Long ID_VALUE = 3l;
	public static final String DESCRIPTION = "Ingredinet description";
	public static final BigDecimal AMOUNT = new BigDecimal(5.02);
	IngredientCommandToIngredient converter;
	
	@Before
	public void setUp() throws Exception {
		converter = new IngredientCommandToIngredient();
	}

	@Test
	public void testConvertNullIngredientCommand() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testConvertEmptyIngredientCommand() throws Exception {
		IngredientCommand ingredientCommand = new IngredientCommand();
		assertEquals(Ingredient.class, converter.convert(ingredientCommand).getClass() );	
	}
	
	@Test
	public void testConvertIngredientCommand() throws Exception {
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(ID_VALUE);
		ingredientCommand.setAmount(AMOUNT);
		ingredientCommand.setDescription(DESCRIPTION);
		
		Ingredient ingredient = converter.convert(ingredientCommand);
		assertEquals(ID_VALUE, ingredient.getId());
		assertEquals(DESCRIPTION, ingredient.getDescription());
		assertEquals(AMOUNT, ingredient.getAmount());
	}

}
