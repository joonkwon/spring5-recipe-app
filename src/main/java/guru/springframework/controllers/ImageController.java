package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ImageController {

	private final RecipeService recipeService;
	private final ImageService imageService;
	
	public ImageController(RecipeService recipeService, ImageService imageService) {
		this.recipeService = recipeService;
		this.imageService = imageService;
	}
	
	@GetMapping("/recipe/{recipeId}/image/upload")
	public String uploadImage(@PathVariable("recipeId") String recipeId, Model model) {
		log.debug("Fetching recipe, ID: " + recipeId);
		model.addAttribute("recipe", recipeService.getCommandById(new Long(recipeId)));
		return "recipe/imageuploadform";
	}
	
	@PostMapping("/recipe/{recipeId}/image/save")
	public String saveImage(@PathVariable("recipeId") String recipeId, @RequestParam("image") MultipartFile file) {
		log.debug("Saving image for recipe ID: " + recipeId);
		imageService.saveImage(new Long(recipeId), file);
		log.debug("Image saved for recipe ID: " + recipeId);
		
		return "redirect:/recipe/" + recipeId + "/show";
	}
}
