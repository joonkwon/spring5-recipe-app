package guru.springframework.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyLong;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;

public class IngredientServiceImplTest {
	
	IngredientService ingredientService;
	
	@Mock
	RecipeService recipeService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ingredientService = new IngredientServiceImpl(recipeService);
	}

	@Test
	public final void testGetCommandByRecipeIdAndIngredientId() {
		//given
		Long recipeId = 2l;
		Long ingredientId = 3l;
		RecipeCommand recipeCommand = new RecipeCommand();
		IngredientCommand ingredientCommand = new IngredientCommand();
		
		recipeCommand.setId(recipeId);
		ingredientCommand.setId(ingredientId);
		
		recipeCommand.getIngredients().add(ingredientCommand);
		
		when(recipeService.getCommandById(recipeId)).thenReturn(recipeCommand);
		
		//when
		IngredientCommand outIngredientCommand = ingredientService.getCommandByRecipeIdAndIngredientId(recipeId, ingredientId);
				
		//then
		verify(recipeService, times(1)).getCommandById(recipeId);
		assertEquals(ingredientId, outIngredientCommand.getId());
	}

	@Test
	public final void testGetCommandByRecipeIdAndNullReturnIngredientId() {
		//given
		Long recipeId = 2l;
		Long ingredientId = 3l;
		RecipeCommand recipeCommand = new RecipeCommand();
		IngredientCommand ingredientCommand = new IngredientCommand();
		
		recipeCommand.setId(recipeId);
		ingredientCommand.setId(ingredientId);
		
		recipeCommand.getIngredients().add(ingredientCommand);
		
		when(recipeService.getCommandById(recipeId)).thenReturn(recipeCommand);
		
		//when
		IngredientCommand outIngredientCommand = ingredientService.getCommandByRecipeIdAndIngredientId(recipeId, 2l);
		
		//then
		assertNull(outIngredientCommand);
	}
	
	@Test
	public final void testGetCommandByNullReturnRecipeIdAndIngredientId() {
		//given
		when(recipeService.getCommandById(anyLong())).thenReturn(null);
		
		//when
		IngredientCommand outIngredientCommand = ingredientService.getCommandByRecipeIdAndIngredientId(1l, 1l);
				
		//then
		assertNull(outIngredientCommand);
	}
}
