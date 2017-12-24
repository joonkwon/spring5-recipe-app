package guru.springframework.converters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;

public class CategoryToCategoryCommandTest {

	public static final Long ID_VALUE = new Long(5L);
	public static final String CATEGORY_NAME = "category name";
	CategoryToCategoryCommand converter;
	
	@Before
	public void setUp() throws Exception {
		converter = new CategoryToCategoryCommand();
	}
	
	@Test
	public void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new Category()));
		assertEquals(
				CategoryCommand.class, 
				converter.convert(new Category()).getClass());	
	}
	
	@Test
	public void testConvert() throws Exception {
		//given
		Category category = new Category();
		category.setId(ID_VALUE);
		category.setCategoryName(CATEGORY_NAME);
		
		//when
		CategoryCommand categoryCommand = converter.convert(category);
		
		//then
		assertEquals(ID_VALUE, categoryCommand.getId());
		assertEquals(CATEGORY_NAME, categoryCommand.getCategoryName());
	}
}
