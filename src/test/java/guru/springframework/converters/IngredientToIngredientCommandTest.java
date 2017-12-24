package guru.springframework.converters;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;

public class IngredientToIngredientCommandTest {

	public static final Long ID_VALUE = 7l;
	public static final String DESCRIPTION = "Ingredient description";
	public static final BigDecimal AMOUNT = new BigDecimal("5");
	
	IngredientToIngredientCommand converter;
	
	@Before
	public void setUp() throws Exception {
		converter = new IngredientToIngredientCommand();	
	}
	
	@Test
	public void testConvertNullIngredient() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testConvertEmptyIngredient() throws Exception {
		assertEquals(IngredientCommand.class, converter.convert(new Ingredient()).getClass());
	}

	@Test
	public void testConvert() {
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ID_VALUE);
		ingredient.setDescription(DESCRIPTION);
		ingredient.setAmount(AMOUNT);
		
		IngredientCommand ingredientCommand = converter.convert(ingredient);
		assertEquals(ID_VALUE, ingredientCommand.getId());
		assertEquals(DESCRIPTION, ingredientCommand.getDescription());
		assertEquals(AMOUNT, ingredientCommand.getAmount());
	}

}
