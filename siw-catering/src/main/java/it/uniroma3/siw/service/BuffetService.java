package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.repository.BuffetRepository;

@Service
public class BuffetService {
	@Autowired BuffetRepository buffetRepository;
	public boolean alreadyExists(Buffet o) {
		if(this.buffetRepository.findByName(o.getNome())!=null)
			return true;
		return false;
	}
	@Transactional
	public Buffet inserisci(Buffet b) {
		return this.buffetRepository.save(b);
	}
	@Transactional
	public void rimuovi(Buffet b) {
		this.buffetRepository.delete(b);
	}
	@Transactional
	public void rimuoviTutto() {
		this.buffetRepository.deleteAll();
	}
	@Transactional
    public void modificaNome(Buffet b) {
        //prima modifichi nome con setNome()
        this.buffetRepository.modificaNomeApp(b.getNome(), b.getId());
    }

    @Transactional
    public void modificaDescr(Buffet b) {
        //prima modifichi nome con setDescr()
        this.buffetRepository.modificaDescrApp(b.getDescr(), b.getId());
    }
}
