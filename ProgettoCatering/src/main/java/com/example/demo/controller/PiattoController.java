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

import com.example.demo.controller.validator.PiattoValidator;
import com.example.demo.model.Buffet;
import com.example.demo.model.Piatto;
import com.example.demo.service.BuffetService;
import com.example.demo.service.IngredienteService;
import com.example.demo.service.PiattoService;

@Controller
public class PiattoController {

	@Autowired
	PiattoService piattoService;
	
	@Autowired 
	PiattoValidator piattoValidator;
	
	@Autowired
	BuffetService buffetService;
	
	@Autowired
	IngredienteService ingredienteService;
	
	
	//aggiunta del piatto nel modello
	@PostMapping("/admin/piatto")
	public String addPiatto(@Valid @ModelAttribute("piatto") Piatto p, BindingResult bindingResult, @RequestParam(name = "buffetScelto") Long id, Model model) {
		
		this.piattoValidator.validate(p, bindingResult);
		
		
		if (!bindingResult.hasErrors()) {
			Buffet b = this.buffetService.searchById(id);
			p.setBuffet(b);
			b.getListaPiatti().add(p);
			this.buffetService.inserisci(b);
			model.addAttribute("piatto", p);
			model.addAttribute("elencoIngredienti",p.getIngredientiDelPiatto());
			return "piatto.html";
		} 
		
		model.addAttribute("piatto", p);
		return "piattoForm.html";
	}

	// Richiede tutti i piatti
	@GetMapping("/elencoPiatti")
	public String getAllPiatti(Model model) {
		List<Piatto> elencoPiatti = this.piattoService.findAllPiatti();
		model.addAttribute("elencoPiatti", elencoPiatti);
		return "elencoPiatti.html";
	}

	//creazione di un nuovo piatto e ritorno del form
	@GetMapping("/admin/piattoForm")
	public String getPiattoForm(Model model) {
		model.addAttribute("piatto", new Piatto());
        model.addAttribute("buffetDisponibili", this.buffetService.findAllBuffet());
		return "piattoForm.html";
	}

	//torna la pagina dedicata al piatto con il determinato id
	@GetMapping("/piatto/{id}")
	public String getPiatto(@PathVariable("id") Long id, Model model){
		Piatto piatto = this.piattoService.searchById(id);
		model.addAttribute("piatto", piatto);
		model.addAttribute("elencoIngredienti", piatto.getIngredientiDelPiatto());
		return "piatto.html";
	}

	//cancellazione piatto
	@GetMapping("/deletePiatti")
	public String deletePiatti(@RequestParam Long piattoId) {
		this.piattoService.rimuovi(piattoId);
		return "redirect:/elencoPiatti";
	}
	
	//aggiornamento del piatto tramite form
	@GetMapping("/admin/updatePiatto")
    public String updatePiattoForm(@RequestParam Long piattoId, Model model) {
        System.out.println("L'id del piatto: " + piattoId);
        model.addAttribute("piatto", this.piattoService.searchById(piattoId));
        model.addAttribute("buffetDisponibili", this.buffetService.findAllBuffet());
        return "piattoUpdateForm.html";
    }

	//modifica effettiva del piatto con il determinato id
	@PostMapping("/piattoUpdate/{id}")
    public String updatePiatto(@Valid @ModelAttribute("piatto") Piatto piatto, BindingResult bindingResult, Model model) {
        this.piattoValidator.validate(piatto, bindingResult);
        if(!bindingResult.hasErrors()) {
            this.piattoService.inserisci(piatto);
            model.addAttribute("piatto", piatto);
            model.addAttribute("buffetDisponibili", this.buffetService.findAllBuffet());
            return "piatto.html";
        }
        else {
            return "piattoUpdateForm.html";
        }
    }
	
}
