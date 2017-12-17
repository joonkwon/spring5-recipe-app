package guru.springframework.bootstraps;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.domain.Category;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BootStrapDB implements ApplicationListener<ContextRefreshedEvent> {

	private final CategoryRepository categoryRepository;
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository uomRepository;
	
	@Autowired
	public BootStrapDB(CategoryRepository categoryRepository, RecipeRepository recipeRepository,
			UnitOfMeasureRepository uomRepository) {
		this.categoryRepository = categoryRepository;
		this.recipeRepository = recipeRepository;
		this.uomRepository = uomRepository;
	}

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.debug("Loading DB");
		recipeRepository.saveAll(getRecipes());		
	}
	
	private List<Recipe> getRecipes(){
		List<Recipe> recipes = new ArrayList<Recipe>();
		
		Category americanCategory = categoryRepository.findByCategoryName("American").get();
		Category italianCategory = categoryRepository.findByCategoryName("Italian").get();
		Category fastFoodCategory = categoryRepository.findByCategoryName("Fast Food").get();
		
		UnitOfMeasure ounceUom = uomRepository.findByUom("Ounce").get();
		UnitOfMeasure cupUom = uomRepository.findByUom("Cup").get();
		UnitOfMeasure teaspoonUom = uomRepository.findByUom("Teaspoon").get();
		UnitOfMeasure pinchUom = uomRepository.findByUom("Pinch").get();
		
		// Almond Shortbread Cookies recipe
		Recipe recipe = new Recipe();
		recipe.setDescription("Almond Shortbread Cookies");
		recipe.getCategories().add(americanCategory);
		recipe.getCategories().add(italianCategory);
		recipe.setDifficulty(Difficulty.MEDIUM);
		recipe.setCookTime(30);
		String directions = "1 Preheat the oven to 350ºF. Generously butter a 9-inch pie pan, or a tart pan with a removable rim.\n" + 
				"2 Mix the dough: In a large bowl, use a wooden spoon to stir the butter and sugar until creamy.\n" + 
				"Stir in the flour, cornstarch, almond flour and salt. Mix until combined. Stir in the vanilla and milk. Knead with your hands if necessary; the dough will come together in big clumps.\n" + 
				"Alternatively, use a stand mixer with a beater attachment to mix the dough. Once you've added the vanilla and milk, beat on low speed just until the dough comes together.\n" + 
				"\n" + 
				"\n" + 
				"Read more: http://www.simplyrecipes.com/recipes/almond_shortbread_cookies/#ixzz51JEK5Yao" +
				"3 Press the dough into the pan: Scrape the dough into the pan and press into an even layer into the bottom of the pan (it should not go up the sides).\n" + 
						"Cover the bottom of a 1-cup dry measuring cup with a piece of plastic wrap and use it to smooth the dough evenly in the pan.\n" + 
						"Almond Shortbread Cookie Almond Shortbread Cookie\n" + 
						"4 Cut the dough into wedges and chill: With a paring knife, cut the dough into 12 or 16 wedges. Scatter the sliced almonds over the top and press them gently into the dough. Chill for 30 minutes, until firm, or up to 2 days. (If chilling overnight or longer, cover the pan with plastic wrap.)\n" + 
						"Almond Shortbread Cookie Almond Shortbread Cookie\n" + 
						"5 Bake the shortbread: Set the pan on a rimmed baking sheet, and bake for 25 minutes, or until the edges begin to brown.\n" + 
						"Cover loosely with foil and continue to bake for 15 to 20 minutes, or until the dough feels firm and looks golden.\n" + 
						"Remove from the oven and let cool for 10 minutes. While the shortbread is still awrm, carefully cut the wedges again to make sure they're separated. Leave in the pan until completely cool.\n" + 
						"6 Store the shortbread: Store the shortbread in an airtight tin for up to 3 days or in the refrigerator for up to 7 days. Sprinkle with confectioner's sugar before serving, if you like.\n";
		recipe.setDirections(directions);
		recipe.getIngredients().add(new Ingredient("unsalted butter, cut into 1-inch slices, at room temperature", new BigDecimal(8), recipe, ounceUom));
		recipe.getIngredients().add(new Ingredient("sugar", new BigDecimal(1), recipe, cupUom));
		recipe.addIngredient(new Ingredient("almond flour", new BigDecimal(1/2), teaspoonUom));
		recipe.setNotes(new Notes("This recipe makes 12 large or 16 small wedges—the cookies are rich, so the small wedges are not too small if you want to have a greater yield."));
		recipe.setUrl("http://www.simplyrecipes.com/recipes/almond_shortbread_cookies/");
		
		recipes.add(recipe);

		//Chicken Fried Rice recipe
		Recipe recipe1 = new Recipe();
		recipe1.setPrepTime(10);
		recipe1.setCookTime(30);
		recipe1.setDifficulty(Difficulty.EASY);
		recipe1.setDescription("Chicken Fried Rice");
		recipe1.setNotes(new Notes("When I was a kid, I always looked forward to days when my mother made fried rice for dinner. Like most Asian families, we ate regular plain rice almost every day with dinner, so any time my mother felt inspired to change up our dinner routine felt like a treat to me!\n" + 
				"Fried rice is a dish that is meant to repurpose leftover rice from a day or two before. The first time I tried cooking fried rice on my own, I made the mistake of using freshly cooked rice. Because it was still so moist and soft, resulting dish was far too gummy — nothing like what my mother cooked. I don’t even remember if I ate much of that batch of fried rice, but I certainly learned a big lesson!\n" + 
				"\n" + 
				"\n" + 
				"Read more: http://www.simplyrecipes.com/recipes/chicken_fried_rice/#ixzz51LbPYLrT"));
		recipe1.setUrl("http://www.simplyrecipes.com/recipes/chicken_fried_rice/");
		recipe1.getCategories().add(fastFoodCategory);
		recipe1.getCategories().add(americanCategory);
		recipe1.addIngredient(new Ingredient("minced ginger", new BigDecimal(3), pinchUom));
		recipe1.addIngredient(new Ingredient("Chinese five-spice powder", new BigDecimal(1.5), teaspoonUom));
		recipe1.addIngredient(new Ingredient("frozen peas, rinsed under warm tap water for a few seconds thawr", new BigDecimal(2/3), cupUom));
		recipe1.setDirections("1 Prepare the chicken: Chop the chicken into small 1/4-inch to 1/2-inch cubes. Sprinkle 1/2 teaspoon of salt over the chicken and mix to combine. Set the chicken aside for about 10 minutes (I usually use this time to chop all the vegetables).\n" + 
				"2 Scramble the egg: Heat a wok or large sauté pan over medium-high heat. Swirl in a tablespoon of oil and add the whisked eggs. Use a spatula to quickly scramble the eggs, breaking the curds into smaller pieces as they come together. Transfer the eggs to a plate.\n" + 
				"3 Cook the chicken: Add another tablespoon of oil in the wok or pan. Add the chicken and cook for 4 to 5 minutes, stirring occasionally. Turn off the heat and transfer the cooked chicken to a plate.\n" + 
				"Using your spatula, scrape off any chicken bits that are still stuck to the wok so they don't burn during the next step. You can also use paper towels to wipe down your wok or pan.\n" + 
				"4 Cook the vegetables: Swirl 1 tablespoon of oil into the wok over medium-high heat. Add the diced onions and cook them for 1 minute, until they start to soften. Mix in the minced garlic and ginger and cook until fragrant, about 30 seconds. Add the diced carrots and cook for 2 minutes, stirring frequently. Add 1/2 teaspoon salt and the peas, and stir to incorporate.\n" + 
				"5 Cook the rice: Add the rice to the wok or pan on top of the vegetables and stir to combine. Using the back of your spatula, smash any large chunks of rice to break them apart. Add the white and green parts of the sliced scallions (save the dark green parts) and five-spice powder. Stir to incorporate. If the rice starts to stick to the pan, stir in a little more oil.\n" + 
				"Drizzle the soy sauce and sesame oil over the rice and stir to incorporate. Stir in the cooked chicken, scrambled eggs, and the dark parts of the scallions. Stir briefly to bring it together, and cook for another 1 to 2 minutes. Taste, and add more soy sauce if necessary.\n" + 
				"6 Serve immediately while hot.\n");
		recipes.add(recipe1);
		
		return recipes;
	}

}
