package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.controller.validator.BuffetValidator;
import com.example.demo.model.Buffet;
import com.example.demo.model.Chef;
import com.example.demo.service.BuffetService;
import com.example.demo.service.ChefService;
import com.example.demo.service.PiattoService;

@Controller
public class BuffetController {
	@Autowired
	BuffetService buffetService;
	@Autowired
	BuffetValidator buffetValidator;
	@Autowired
	PiattoService piattoService;
	@Autowired
	ChefService chefService;
	

	
/*
 * *
 * 
 * aggiunta del buffet nel modello
 * 
 */
	@PostMapping("/admin/buffet")
	public String addBuffet(@Valid @ModelAttribute("buffet") Buffet b, BindingResult bindingResult, @RequestParam(name = "chefScelto") Long id, Model model) {
		
		this.buffetValidator.validate(b, bindingResult);
		
		if (!bindingResult.hasErrors()) {
			Chef c = this.chefService.searchById(id);
			b.setChefDelBuffet(c);
			c.getBuffetDelloChef().add(b);
			this.chefService.inserisci(c);
			
			
			model.addAttribute("buffet", b);
			model.addAttribute("elencoPiatti",b.getListaPiatti());
			return "buffet.html";

		} 
		model.addAttribute("buffet", b);
		return "buffetForm.html";
		
	}

	// Richiede tutti i buffet
	@GetMapping("/elencoBuffet")
	public String getAllBuffet(Model model) {
		List<Buffet> elencoBuffet = this.buffetService.findAllBuffet();
		model.addAttribute("elencoBuffet", elencoBuffet);
		return "elencoBuffet.html";
	}


	//creazione di un nuovo buffet e ritorno del form
	@GetMapping("/admin/buffetForm")
	public String getBuffetForm(Model model) {
		model.addAttribute("buffet", new Buffet());
		model.addAttribute("chefDisponibili", this.chefService.findAllChef());
		return "buffetForm.html";
	}

	
	//torna la pagina dedicata al buffet con il determinato id
	@GetMapping("/buffet/{id}")
	public String getBuffet(@PathVariable("id") Long id, Model model){
		Buffet buffet = this.buffetService.searchById(id);
		model.addAttribute("buffet", buffet);
		model.addAttribute("elencoPiatti", buffet.getListaPiatti());
		return "buffet.html";
	}

	//cancellazione buffet
	@GetMapping("/deleteBuffet")
	public String deleteBuffet(@RequestParam Long buffetId) {
		this.buffetService.rimuovi(buffetId);
		return "redirect:/elencoBuffet";
	}

	//aggiornamento del buffet tramite form
	@GetMapping("/admin/updateBuffet")
    public String updateBuffetForm(@RequestParam Long buffetId, Model model) {
        System.out.println("L'id del buffet: " + buffetId);
        model.addAttribute("buffet", this.buffetService.searchById(buffetId));
        model.addAttribute("chefDisponibili", this.chefService.findAllChef());
        return "buffetUpdateForm.html";
    }

	//modifica effettiva del buffet con il determinato id
	@PostMapping("/buffetUpdate/{id}")
    public String updateBuffet(@Valid @ModelAttribute("buffet") Buffet buffet, BindingResult bindingResult, Model model) {
        this.buffetValidator.validate(buffet, bindingResult);
        if(!bindingResult.hasErrors()) {
            this.buffetService.inserisci(buffet);
            model.addAttribute("buffet", buffet);
            model.addAttribute("chefDisponibili", this.chefService.findAllChef());
            return "buffet.html";
        }
        else {
            return "buffetUpdateForm.html";
        }
    }
	




}
