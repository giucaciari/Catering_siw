package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.repository.ChefRepository;

@Service
public class ChefService {
	@Autowired ChefRepository chefRepository;
	public boolean alreadyExists(Chef o) {
		if(this.chefRepository.findByNomeAndCognome(o.getNome(),o.getCognome())!=null)
			return true;
		return false;
		
	}
	@Transactional
	public Chef inserisci(Chef b) {
		return this.chefRepository.save(b);
	}
	@Transactional
	public void rimuovi(Chef b) {
		this.chefRepository.delete(b);
	}
	@Transactional
	public void rimuoviTutto() {
		this.chefRepository.deleteAll();
	}
}
