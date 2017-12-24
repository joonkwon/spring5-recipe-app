package guru.springframework.commands;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Setter
@Getter
@NoArgsConstructor
public class CategoryCommand {
	private Long id;
	private String categoryName;
		
}
