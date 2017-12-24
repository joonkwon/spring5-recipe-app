package guru.springframework.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

	@Override
	public UnitOfMeasure convert(UnitOfMeasureCommand source) {
		if (source == null) {
			return null;
		}
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId(source.getId());
		uom.setUom(source.getUom());
		
		return uom;
	}

}
