package guru.springframework.services;

import java.util.Set;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;

public interface UnitOfMeasureService {

	Set<UnitOfMeasure> getAllUom();
	Set<UnitOfMeasureCommand> getAllUomCommand();
}
