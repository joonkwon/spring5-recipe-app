package guru.springframework.services;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

	private final RecipeRepository recipeRepository;
	
	public ImageServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	@Transactional
	public void saveImage(Long recipeId, MultipartFile file) {
		log.debug("Fetching recipe, ID: " + recipeId);
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		if (!recipeOptional.isPresent()) {
			log.debug("recipe not found, ID: " + recipeId);
		}
		Recipe recipe = recipeOptional.get();
		try {
			Byte[] byteObjects = new Byte[file.getBytes().length];
			
			for (int i = 0; i < file.getBytes().length; i++) {
				byteObjects[i] = file.getBytes()[i];
			}
			
			recipe.setImage(byteObjects);
			recipeRepository.save(recipe);
			
		} catch (IOException e) {
			log.error("Exception occured: " + e);
			e.printStackTrace();
		}
	}
	
	@Override
	@Transactional
	public Byte[] getRecipeImage(Long recipeId) {
		log.debug("Fetching recipe, ID: " + recipeId);
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		if (!recipeOptional.isPresent()) {
			log.debug("recipe not found, ID: " + recipeId);
		}
		if (recipeOptional.get().getImage() == null) {
			log.debug("Image not found for recipe ID: " + recipeId);
		}
		return recipeOptional.get().getImage();		
	}

}
