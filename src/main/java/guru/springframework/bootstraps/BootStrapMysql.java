package guru.springframework.bootstraps;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import guru.springframework.domain.Category;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile({"dev", "prod"})
public class BootStrapMysql implements ApplicationListener<ContextRefreshedEvent>  {

	private CategoryRepository categoryRepo;
	private UnitOfMeasureRepository uomRepo;
	
	public BootStrapMysql(CategoryRepository categoryRepo, UnitOfMeasureRepository uomRepo) {
		super();
		this.categoryRepo = categoryRepo;
		this.uomRepo = uomRepo;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (categoryRepo.count() == 0L) {
			log.debug("Loading categories");
			loadCategories();
		}
		
		if (uomRepo.count() == 0L) {
			log.debug("Loading unit of measures");
			loadUom();
		}
	}
	
	private void loadCategories(){
        Category cat1 = new Category();
       
        cat1.setCategoryName("American");
        categoryRepo.save(cat1);

        Category cat2 = new Category();
        cat2.setCategoryName("Italian");
        categoryRepo.save(cat2);

        Category cat3 = new Category();
        cat3.setCategoryName("Mexican");
        categoryRepo.save(cat3);

        Category cat4 = new Category();
        cat4.setCategoryName("Fast Food");
        categoryRepo.save(cat4);
    }

	private void loadUom(){
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setUom("Teaspoon");
        uomRepo.save(uom1);
        
        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setUom("Tablespoon");
        uomRepo.save(uom2);

        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setUom("Cup");
        uomRepo.save(uom3);

        UnitOfMeasure uom4 = new UnitOfMeasure();
        uom4.setUom("Pinch");
        uomRepo.save(uom4);

        UnitOfMeasure uom5 = new UnitOfMeasure();
        uom5.setUom("Ounce");
        uomRepo.save(uom5);

        UnitOfMeasure uom6 = new UnitOfMeasure();
        uom6.setUom("Each");
        uomRepo.save(uom6);

        UnitOfMeasure uom7 = new UnitOfMeasure();
        uom7.setUom("Pint");
        uomRepo.save(uom7);

        UnitOfMeasure uom8 = new UnitOfMeasure();
        uom8.setUom("Dash");
        uomRepo.save(uom8);
    }
}
