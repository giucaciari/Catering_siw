package it.uniroma3.siw.repository;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.*;
public interface PiattoRepository extends CrudRepository<Piatto,Long> {
	public Piatto findByNome(String s);
}
