package guru.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

	@Override
	public UnitOfMeasureCommand convert(UnitOfMeasure source) {
		if (source == null) {
			return null;
		}
		
		UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
		uomCommand.setId(source.getId());
		uomCommand.setUom(source.getUom());
		
		return uomCommand;
	}

}
