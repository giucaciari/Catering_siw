package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import it.uniroma3.siw.controller.validator.ChefValidator;
import it.uniroma3.siw.service.ChefService;

@Controller
public class ChefController {
	@Autowired ChefService chefService;
	@Autowired ChefValidator chefValidator;
}
