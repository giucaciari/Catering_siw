package it.uniroma3.siw.repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import it.uniroma3.siw.model.*;
public interface BuffetRepository extends CrudRepository<Buffet,Long> {
	public Buffet findByName(String s);
	@Modifying
    @Query("update Buffet b set b.nome = :nome where b.id == :id")
    public void modificaNomeApp(@Param("nome") String nome, @Param("id") Long id);


    @Modifying
    @Query("update Buffet b set b.descr = :descr where b.id == :id")
    public void modificaDescrApp(@Param("descr") String descr, @Param("id") Long id);
}
