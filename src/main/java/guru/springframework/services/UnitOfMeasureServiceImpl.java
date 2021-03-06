package guru.springframework.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	private final UnitOfMeasureRepository uomRepository;
	private final UnitOfMeasureToUnitOfMeasureCommand uomToCommand;
	
	@Autowired
	public UnitOfMeasureServiceImpl(UnitOfMeasureRepository uomRepository,
			UnitOfMeasureToUnitOfMeasureCommand uomToCommand) {
		this.uomRepository = uomRepository;
		this.uomToCommand = uomToCommand;
	}

	@Override
	public Set<UnitOfMeasure> getAllUom() {
		Set<UnitOfMeasure> allUom = new HashSet<UnitOfMeasure>();
		log.debug("Fetching all unit of measure");
		uomRepository.findAll().forEach(allUom::add);
		if (allUom.size() == 0) {
			log.debug("No unit of measure found");
			return null;
		}
		return allUom;
	}
	
	@Override
	public Set<UnitOfMeasureCommand> getAllUomCommand(){
		Set<UnitOfMeasureCommand> allUomCommand = new HashSet<UnitOfMeasureCommand>();
		for (UnitOfMeasure uom: getAllUom()) {
			allUomCommand.add(uomToCommand.convert(uom));
		}
		
		return allUomCommand;
	}

}
