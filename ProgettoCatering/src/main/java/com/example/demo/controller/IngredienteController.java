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

import com.example.demo.controller.validator.IngredienteValidator;
import com.example.demo.model.Ingrediente;
import com.example.demo.model.Piatto;
import com.example.demo.service.IngredienteService;
import com.example.demo.service.PiattoService;


@Controller
public class IngredienteController {
	
	@Autowired
	IngredienteService ingredienteService;
	@Autowired
	IngredienteValidator ingredienteValidator;
	@Autowired
	PiattoService piattoService;
	
	//aggiunta dell'ingrediente nel modello
	@PostMapping("/admin/ingrediente")
	public String addIngrediente(@Valid @ModelAttribute("ingrediente") Ingrediente i, BindingResult bindingResult, @RequestParam(name = "piattoScelto") Long id, Model model) {
		
		this.ingredienteValidator.validate(i, bindingResult);
		
		if (!bindingResult.hasErrors()) {
			Piatto p = this.piattoService.searchById(id);
			i.setPiatto(p);
			p.getIngredientiDelPiatto().add(i);
			this.piattoService.inserisci(p);
			model.addAttribute("ingrediente", i);
			return "ingrediente.html";

		} 
		model.addAttribute("ingrediente", i);
		return "ingredienteForm.html";
		
	}

	// Richiede tutti gli ingredienti
	@GetMapping("/elencoIngredienti")
	public String getAllIngredienti(Model model) {
		List<Ingrediente> elencoIngredienti = this.ingredienteService.findAllIngredienti();
		model.addAttribute("elencoIngredienti", elencoIngredienti);
		return "elencoIngredienti.html";
	}

	//creazione di un nuovo ingrediente e ritorno del form
	@GetMapping("/admin/ingredienteForm")
	public String getIngredientiForm(Model model) {
		model.addAttribute("ingrediente", new Ingrediente());
		model.addAttribute("piattiDisponibili", this.piattoService.findAllPiatti());
		return "ingredienteForm.html";
	}

	//torna la pagina dedicata all'ingrediente con il determinato id
	@GetMapping("/ingrediente/{id}")
	public String getIngrediente(@PathVariable("id") Long id, Model model){
		Ingrediente ingrediente = this.ingredienteService.searchById(id);
		model.addAttribute("ingrediente", ingrediente);
		return "ingrediente.html";
	}

	//cancellazione ingrediente
	@GetMapping("/deleteIngredienti")
	public String deleteIngredienti(@RequestParam Long ingredienteId) {
		this.ingredienteService.rimuovi(ingredienteId);
		return "redirect:/elencoIngredienti";
	}
	
	//aggiornamento dell'ingrediente tramite form
	@GetMapping("/admin/updateIngrediente")
    public String updateIngredienteForm(@RequestParam Long ingredienteId, Model model) {
        System.out.println("L'id dell'ingrediente: " + ingredienteId);
        model.addAttribute("ingrediente", this.ingredienteService.searchById(ingredienteId));
        return "ingredienteUpdateForm.html";
    }

	//modifica effettiva dell'ingrediente con il determinato id
	@PostMapping("/ingredienteUpdate/{id}")
    public String updateIngrediente(@Valid @ModelAttribute("ingrediente") Ingrediente ingrediente, BindingResult bindingResult, Model model) {
        this.ingredienteValidator.validate(ingrediente, bindingResult);
        if(!bindingResult.hasErrors()) {
            this.ingredienteService.inserisci(ingrediente);
            model.addAttribute("ingrediente", ingrediente);
            return "ingrediente.html";
        }
        else {
            return "ingredienteUpdateForm.html";
        }
    }

}
