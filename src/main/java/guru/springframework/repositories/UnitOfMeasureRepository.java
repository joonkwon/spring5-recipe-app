package guru.springframework.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.domain.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

	Optional<UnitOfMeasure> findByUom(String uom);
}
