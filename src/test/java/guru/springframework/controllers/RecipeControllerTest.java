package guru.springframework.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;

public class RecipeControllerTest {
	
	RecipeController recipeController;
	
	@Mock
	RecipeService recipeService;
	
	@Mock
	Model model;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeController = new RecipeController(recipeService);
	}
	
	@Test
	public void testShowRecipe() throws Exception {
		Long id = 3l;
		Recipe recipe = new Recipe();
		recipe.setId(id);
		
		when(recipeService.getRecipeById(id)).thenReturn(recipe);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
		mockMvc.perform(get("/recipe/show/" + id))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/show"))
			.andExpect(model().attributeExists("recipe"));
	}
	
}
