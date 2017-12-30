package guru.springframework.controllers;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;

public class IngredientControllerTest {
	
	IngredientController ingredientController;
	
	MockMvc mockMvc;
	
	@Mock
	IngredientService ingredientService;
	
	@Mock
	UnitOfMeasureService uomService;
	
	@Mock
	RecipeService recipeService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ingredientController = new IngredientController(ingredientService, uomService, recipeService);		
		mockMvc = MockMvcBuilders.standaloneSetup(ingredientController)
				.setControllerAdvice(new ControllerExceptionHandler())
				.build();
	}

	@Test
	public void testShowIngredient() throws Exception {
		// given
		IngredientCommand ingCom = new IngredientCommand();
		ingCom.setId(1l);
		when(ingredientService.getCommandByRecipeIdAndIngredientId(anyLong(), anyLong()))
			.thenReturn(ingCom);
		
		// when and then
		mockMvc.perform(get("/recipe/3/ingredient/1/show"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/ingredient/show"))
			.andExpect(model().attributeExists("ingredient"));
		
	}

}
