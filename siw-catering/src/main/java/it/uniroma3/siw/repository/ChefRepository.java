package it.uniroma3.siw.repository;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.*;
public interface ChefRepository extends CrudRepository<Chef,Long>{
	public Chef findByNomeAndCognome(String s,String d);
}
