package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.repository.PiattoRepository;

@Service
public class PiattoService {
	@Autowired PiattoRepository piattoRepository;
	public boolean alreadyExists(Piatto p) {
		if(this.piattoRepository.findByNome(p.getNome())!=null)
			return true;
		return false;
	}
	@Transactional
	public Piatto inserisci(Piatto b) {
		return this.piattoRepository.save(b);
	}
	@Transactional
	public void rimuovi(Piatto b) {
		this.piattoRepository.delete(b);
	}
	@Transactional
	public void rimuoviTutti() {
		this.piattoRepository.deleteAll();
	}
}
