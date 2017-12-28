package guru.springframework.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
	
	private RecipeService recipeService;
	private RecipeRepository recipeRepository;
	
	@Autowired
	public IngredientServiceImpl(RecipeService recipeService, RecipeRepository recipeRepository) {
		this.recipeService = recipeService;
		this.recipeRepository = recipeRepository;
	}
	
	@Override
	public IngredientCommand getCommandByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		RecipeCommand recipeCommand = recipeService.getCommandById(recipeId);
		if (recipeCommand != null) {
			Set<IngredientCommand> ingredientCommands = recipeCommand.getIngredients();
			for (IngredientCommand inCom : ingredientCommands) {
				if (ingredientId.equals(inCom.getId())) {
					return inCom;
				} 	
			}
		}
		log.debug("No ingredient with recipe ID: " + recipeId + 
				  ", ingredient ID: "+ ingredientId);
		return null;
	}

	@Override
	public Set<IngredientCommand> getIngredientCommandsByrecipeId(Long recipeId) {
		log.debug("Fetching recipe, ID: " + recipeId);
		RecipeCommand recipeCommand = recipeService.getCommandById(recipeId);
		
		if (recipeCommand == null) {
			log.debug("Recipe not found, ID: " + recipeId);
			return null;
		}
		
		Set<IngredientCommand> ingredientCommands = recipeCommand.getIngredients();
		
		if (ingredientCommands == null) {
			log.debug("No ingredient found for recipe ID: " + recipeId);
			return null;
		}
		
		return ingredientCommands;
	}
	
	@Transactional
	@Override
	public IngredientCommand saveIngredientCommand(IngredientCommand command, Long recipeId) {
		log.debug("Fetching the recipeCommand for the ingredientCommand ");
		RecipeCommand recipeCommand = recipeService.getCommandById(recipeId);
		Optional<IngredientCommand> ingredientCommandOptional = recipeCommand.getIngredients()
				.stream()
				.filter(s -> s.getId().equals(command.getId()))
				.findFirst();
		if (ingredientCommandOptional.isPresent()) {
				IngredientCommand ingredientCommand = ingredientCommandOptional.get();
				ingredientCommand.setAmount(command.getAmount());
				ingredientCommand.setDescription(command.getDescription());
				ingredientCommand.setUom(command.getUom());
		} else {
			recipeCommand.getIngredients().add(command);
		}
		log.debug("Saving recipe with modified ingredient - ingredient id: " + command.getId());
		RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
		log.debug("Modified ingredient saved for recipe id:" + savedRecipeCommand.getId());
		return savedRecipeCommand
				.getIngredients()
				.stream()
				.filter(s -> s.getId().equals(command.getId()))
				.findFirst()
				.get();
	}
	
	@Override
	@Transactional
	public void deleteById(Long recipeId, Long ingredientId) {
		log.debug("Finding ingredient (ID: " + ingredientId + "with recipe Id: " + recipeId);
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		if (recipeOptional.isPresent()) {
			log.debug("Recipe found");
			Recipe recipe = recipeOptional.get();
			Optional<Ingredient> ingredientOptional = recipe.getIngredients()
					.stream()
					.filter(ing -> ing.getId().equals(ingredientId))
					.findFirst();
			if (ingredientOptional.isPresent()) {
				recipe.getIngredients().remove(ingredientOptional.get());
				recipeRepository.save(recipe);
				log.debug("Ingredient deleted");
			} else {
				log.debug("Ingredient not found");
			}
		} else {
			log.debug("Recipe not found");
		}
		
	}

	

}
