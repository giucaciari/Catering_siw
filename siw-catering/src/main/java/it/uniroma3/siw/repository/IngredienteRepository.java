package it.uniroma3.siw.repository;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.*;
public interface IngredienteRepository extends CrudRepository<Ingrediente,Long> {
	public Ingrediente findByName(String s);
}
