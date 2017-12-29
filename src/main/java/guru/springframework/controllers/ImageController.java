package guru.springframework.controllers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
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

	@GetMapping("/recipe/{recipeId}/image")
	public void getImage(@PathVariable("recipeId") String recipeId, HttpServletResponse response) {
		log.debug("Fetching image for recipe ID: " + recipeId);
		Byte[] imageBytes = imageService.getRecipeImage(new Long(recipeId));
		if (imageBytes != null) {
			byte[] byteArray = new byte[imageBytes.length];
			int i = 0;
			for (Byte b : imageBytes) {
				byteArray[i] = b;
				i++ ;
			}
			response.setContentType("image/jpeg");
			InputStream is = new ByteArrayInputStream(byteArray);
			try {
				IOUtils.copy(is, response.getOutputStream());
			} catch (Exception e) {
				log.error("Exception occured: " + e);
				e.printStackTrace();
			}		
		}
	}
}
