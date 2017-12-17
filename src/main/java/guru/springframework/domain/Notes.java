package guru.springframework.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(exclude={"recipe"})
public class Notes {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	private Recipe recipe;
	
	@Lob
	private String recipeNotes;
	
	public Notes() {};
	
	public Notes(String recipeNotes) {
		this.recipeNotes = recipeNotes;
	}
	public Notes(Recipe recipe, String recipeNotes) {
		this.recipe = recipe;
		this.recipeNotes = recipeNotes;
	}
	
}
