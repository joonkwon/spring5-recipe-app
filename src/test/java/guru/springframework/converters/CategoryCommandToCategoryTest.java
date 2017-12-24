package guru.springframework.converters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;

public class CategoryCommandToCategoryTest {

	public static final Long ID_VALUE = new Long(5L);
	public static final String CATEGORY_NAME = "category name";
	CategoryCommandToCategory converter;
	
	@Before
	public void setUp() throws Exception {
		converter = new CategoryCommandToCategory();
	}
	
	@Test
	public void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}
	
	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new CategoryCommand()));
		assertEquals(
				Category.class, 
				converter.convert(new CategoryCommand()).getClass());	
	}
	
	@Test
	public void testConvert() throws Exception {
		//given
		CategoryCommand categoryCommand = new CategoryCommand();
		categoryCommand.setId(ID_VALUE);
		categoryCommand.setCategoryName(CATEGORY_NAME);
		
		//when
		Category category = converter.convert(categoryCommand);
		
		//then
		assertEquals(ID_VALUE, category.getId());
		assertEquals(CATEGORY_NAME, category.getCategoryName());
	}
}
