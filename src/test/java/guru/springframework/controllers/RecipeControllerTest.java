package guru.springframework.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;

public class RecipeControllerTest {
	
	RecipeController recipeController;
	MockMvc mockMvc;
	
	@Mock
	RecipeService recipeService;
	
	@Mock
	Model model;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeController = new RecipeController(recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
	}
	
	@Test
	public void testShowRecipe() throws Exception {
		Long id = 3l;
		Recipe recipe = new Recipe();
		recipe.setId(id);
		
		when(recipeService.getRecipeById(id)).thenReturn(recipe);
		
		mockMvc.perform(get("/recipe/" + id + "/show/"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/show"))
			.andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	public void testUpdateRecipe() throws Exception {
		Long id = 5l;
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(id);
		
		when(recipeService.getCommandById(id)).thenReturn(recipeCommand);
		
		mockMvc.perform(get("/recipe/" + id + "/update"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/recipeform"))
			.andExpect(model().attributeExists("recipe"));
	}
	@Test
	public void testNewRecipe() throws Exception {
		mockMvc.perform(get("/recipe/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/recipeform"))
			.andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void testPostRecipe() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(5l);
		
		when(recipeService.saveRecipeCommand(any())).thenReturn(command);
		mockMvc.perform(
				post("/recipe")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "5")
				.param("description", "any description")
				)
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:recipe/5/show"));
	}
	
	@Test
	public void testDeleteRecipe() throws Exception {
		mockMvc.perform(get("/recipe/11/delete"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/"));
		
		verify(recipeService, times(1)).deleteById(11l);
	}
}
