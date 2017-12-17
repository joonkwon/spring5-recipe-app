package guru.springframework.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RecipeTest {

	private Recipe recipe;
	private Ingredient ingredient;
	private Notes notes;
		
	@Before
	public void setUp() throws Exception {
		recipe = new Recipe();
		recipe.setId(45l);
		recipe.setDirections("Direction string");
		
		ingredient = new Ingredient();
		ingredient.setDescription("an ingredient description");
		ingredient.setId(4l);
		
		notes = new Notes("Note content");
		notes.setId(5l);
	}

	@Test
	public void testAddIngredient() {
		recipe.addIngredient(ingredient);
		assertTrue(recipe.getIngredients().contains(ingredient));
		assertEquals(ingredient.getRecipe(),recipe);		
	}
	
	@Test
	public void testSetNotes() {
		recipe.setNotes(notes);
		assertEquals(recipe.getNotes(), notes);
		assertEquals(notes.getRecipe(),recipe);
	}

}
