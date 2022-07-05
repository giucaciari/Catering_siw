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

import com.example.demo.controller.validator.ChefValidator;
import com.example.demo.model.Chef;
import com.example.demo.service.ChefService;

@Controller
public class ChefController {
	
	@Autowired
	ChefService chefService;
	@Autowired
	ChefValidator chefValidator;
	
	
	//aggiunta dello chef nel modello
	@PostMapping("/admin/chef")
	public String addChef(@Valid @ModelAttribute("chef") Chef c, BindingResult bindingResult, Model model) {
		this.chefValidator.validate(c, bindingResult);
		if (!bindingResult.hasErrors()) {
			this.chefService.inserisci(c);
			model.addAttribute("chef", this.chefService.searchById(c.getId()));
			model.addAttribute("elencoBuffet", c.getBuffetDelloChef());
			return "chef.html";

		} else {
			return "chefForm.html";
		}
	}

	// Richiede tutti gli chef
	@GetMapping("/elencoChef")
	public String getAllChef(Model model) {
		List<Chef> elencoChef = this.chefService.findAllChef();
		model.addAttribute("elencoChef", elencoChef);
		return "elencoChef.html";
	}

	//creazione di un nuovo chef e ritorno del form
	@GetMapping("/admin/chefForm")
	public String getChefForm(Model model) {
		model.addAttribute("chef", new Chef());
		return "chefForm.html";
	}
	
	//torna la pagina dedicata allo chef con il determinato id
	@GetMapping("/chef/{id}")
	public String getChef(@PathVariable("id") Long id, Model model){
		Chef chef = this.chefService.searchById(id);
		model.addAttribute("chef", chef);
		model.addAttribute("elencoBuffet", chef.getBuffetDelloChef());
		return "chef.html";
	}

	//cancellazione chef
	@GetMapping("/deleteChef")
	public String deleteChef(@RequestParam Long chefId) {
		this.chefService.rimuovi(chefId);
		return "redirect:/elencoChef";
	}
	
	//aggiornamento dello chef tramite form
	@GetMapping("/admin/updateChef")
    public String updateChefForm(@RequestParam Long chefId, Model model) {
        System.out.println("L'id dello chef: " + chefId);
        model.addAttribute("chef", this.chefService.searchById(chefId));
        return "chefUpdateForm.html";
    }

	//modifica effettiva dello chef con il determinato id
	@PostMapping("/chefUpdate/{id}")
    public String updateChef(@Valid @ModelAttribute("chef") Chef chef, BindingResult bindingResult, Model model) {
        this.chefValidator.validate(chef, bindingResult);
        if(!bindingResult.hasErrors()) {
            this.chefService.inserisci(chef);
            model.addAttribute("chef", chef);
            return "chef.html";
        }
        else {
            return "chefUpdateForm.html";
        }
    }
	
}
