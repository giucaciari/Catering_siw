package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.repository.*;
@Service
public class IngredienteService {
	@Autowired IngredienteRepository ingredienteRepository;
	public boolean alreadyExists(Ingrediente o) {
		if(this.ingredienteRepository.findByName(o.getNome())!=null)
			return true;
		return false;
	}
	@Transactional
	public Ingrediente inserisci(Ingrediente b) {
		return this.ingredienteRepository.save(b);
	}
	@Transactional
	public void rimuovi(Ingrediente b) {
		this.ingredienteRepository.delete(b);
	}
	@Transactional
	public void rimuoviTutto() {
		this.ingredienteRepository.deleteAll();
	}
}
