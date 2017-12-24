package guru.springframework.converters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.NotesCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;

public class RecipeCommandToRecipeTest {
	public static final Long ID_VALUE = 7l;
	public static final String DESCRIPTION = "Recipe Description";
	public static final Integer PREPTIME = 10;
	public static final Integer COOKTIME = 15;
	public static final Integer SERVINGS = 4;
	public static final String SOURCE = "Simple Recipes";
	public static final String URL = "http://www.simpletest.com";
	
	public static final Long CAT_ID1 = 9l;
	public static final Long CAT_ID2 = 11l;
	public static final Long ING_ID1 = 7l;
	public static final Long ING_ID2 = 125l;
	public static final Long NOTES_ID = 345l;
	
	RecipeCommandToRecipe converter;
	
	@Before
	public void setUp() throws Exception {
		converter = new RecipeCommandToRecipe(new NotesCommandToNotes(), 
				new CategoryCommandToCategory(), new IngredientCommandToIngredient());		
	}

	@Test
	public void testNullConvert() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyConvert() throws Exception {
		assertEquals(Recipe.class, converter.convert(new RecipeCommand()).getClass());
	}
	
	@Test
	public final void testConvert() throws Exception {
		
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(ID_VALUE);
		recipeCommand.setDescription(DESCRIPTION);
		recipeCommand.setPrepTime(PREPTIME);
		recipeCommand.setCookTime(COOKTIME);
		recipeCommand.setServings(SERVINGS);
		recipeCommand.setSource(SOURCE);
		recipeCommand.setUrl(URL);
		
		NotesCommand notesCommand = new NotesCommand();
		notesCommand.setId(NOTES_ID);
		
		CategoryCommand categoryCommand1 = new CategoryCommand();
		CategoryCommand categoryCommand2 = new CategoryCommand();
		categoryCommand1.setId(CAT_ID1);
		categoryCommand2.setId(CAT_ID2);
		
		IngredientCommand ingredientCommand1 = new IngredientCommand();
		IngredientCommand ingredientCommand2 = new IngredientCommand();
		ingredientCommand1.setId(ING_ID1);
		ingredientCommand2.setId(ING_ID2);
		
		recipeCommand.setNotes(notesCommand);
		recipeCommand.getCategories().add(categoryCommand1);
		recipeCommand.getCategories().add(categoryCommand2);
		recipeCommand.getIngredients().add(ingredientCommand1);
		recipeCommand.getIngredients().add(ingredientCommand2);
		
		Recipe recipe = converter.convert(recipeCommand);
		assertEquals(ID_VALUE,recipe.getId());
		assertEquals(2, recipe.getCategories().size());
		assertEquals(2, recipe.getIngredients().size());
		
	}

}
