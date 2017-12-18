/**
 * 
 */
package guru.springframework.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;

public class RecipeServiceImplTest {
	
	RecipeService recipeService;
	
	@Mock
	RecipeRepository recipeRepository;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		recipeService = new RecipeServiceImpl(recipeRepository);
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
}
