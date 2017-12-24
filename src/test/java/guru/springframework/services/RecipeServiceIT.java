package guru.springframework.services;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {
	
	@Autowired
	RecipeCommandToRecipe recipeCommandToRecipe;
	
	@Autowired
	RecipeToRecipeCommand recipeToRecipeCommand;
	
	@Autowired
	RecipeService recipeService;
	
	@Test
	public void testGetRecipes() {
		//when
		Set<Recipe> recipes = recipeService.getRecipes();
		
		//then
		assertTrue(recipes.size() > 0);
	}
	
	@Test
	public void testGetRecipeById() {
		//given
		Long id = recipeService.getRecipes().iterator().next().getId();
		
		//when
		Recipe recipe = recipeService.getRecipeById(id);
		
		//then
		assertNotNull(recipe);
		assertEquals(id, recipe.getId());
	}
	
	@Test
	@Transactional
	public void testSaveRecipeCommandDescription() {
		String description = "this is a test description";
		Recipe testRecipe = recipeService.getRecipes().iterator().next();
		testRecipe.setDescription(description);
		
		RecipeCommand savedRecipeCommand = recipeService
				.saveRecipeCommand(recipeToRecipeCommand.convert(testRecipe));
		
		assertEquals(description, savedRecipeCommand.getDescription());
		assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
		assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
	}
}
