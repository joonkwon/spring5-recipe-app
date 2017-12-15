package guru.springframework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.services.RecipeService;

@Controller
public class IndexController {
	
	private final RecipeService recipeService;
	
	@Autowired
	public IndexController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}


	@RequestMapping({"", "/"})
	public String index(Model model) {
		model.addAttribute("recipes", recipeService.getRecipes());
		return "index";
	}
}
