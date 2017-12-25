/**
 * 
 */
package guru.springframework.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.any;

import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;

public class RecipeServiceImplTest {
	
	RecipeService recipeService;
	
	@Mock
	RecipeCommandToRecipe recipeCommandToRecipe;
	
	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;
	
	@Mock
	RecipeRepository recipeRepository;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		recipeService = new RecipeServiceImpl(recipeRepository, 
				recipeCommandToRecipe,
				recipeToRecipeCommand);
	}

	@Test
	public void testGetRecipes() {
		Set<Recipe> recipes = new HashSet<Recipe>();
		Recipe recipe = new Recipe();
		recipes.add(recipe);
		
		when(recipeRepository.findAll()).thenReturn(recipes);
		assertEquals(recipeService.getRecipes(), recipes);
		verify(recipeRepository, times(1)).findAll();	
	}

	@Test
	public void getRecipeByIdTest() {
		//create a recipe optional object
		Long id = new Long(3);
		Recipe recipe = new Recipe();
		recipe.setId(id);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(id)).thenReturn(recipeOptional);
		assertEquals(recipe, recipeService.getRecipeById(id));
		verify(recipeRepository, times(1)).findById(id);
	}
	
	@Test
	public void getCommandByIdTest() throws Exception {
		Recipe recipe = new Recipe();
		Long id = 4l;		
		recipe.setId(id);
		
		RecipeCommand command = new RecipeCommand();
		command.setId(4l);

		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		when(recipeToRecipeCommand.convert(any())).thenReturn(command);
		
		RecipeCommand recipeCommand = recipeService.getCommandById(4l);
		
		assertEquals(id, recipeCommand.getId());
		verify(recipeToRecipeCommand, times(1)).convert(recipe);
		verify(recipeRepository, times(1)).findById(id);
	}
	
	@Test
	public void deleteByIdTest() throws Exception {
		//given
		Long id = 7l;
		
		//when
		recipeService.deleteById(id);
		
		//then
		verify(recipeRepository, times(1)).deleteById(id);
	}
}
