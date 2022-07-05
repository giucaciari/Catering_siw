package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.controller.validator.CredentialsValidator;
import com.example.demo.controller.validator.UserValidator;
import com.example.demo.model.Buffet;
import com.example.demo.model.Chef;
import com.example.demo.model.Credentials;
import com.example.demo.model.Ingrediente;
import com.example.demo.model.Piatto;
import com.example.demo.model.User;
import com.example.demo.service.BuffetService;
import com.example.demo.service.ChefService;
import com.example.demo.service.CredentialsService;
import com.example.demo.service.IngredienteService;
import com.example.demo.service.PiattoService;

@Controller

public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private CredentialsValidator credentialsValidator;
	
	@Autowired
	PiattoService ps;
	@Autowired
	BuffetService bs;
	@Autowired
	IngredienteService is;
	@Autowired
	ChefService cs;
	
	
	
	@GetMapping("/register") 
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "register.html";
	}
	
	@GetMapping("/login") 
	public String showLoginForm (Model model) {
		return "accessoAmministratore.html";
	}
	
	
	@GetMapping("/logout") 
	public String logout(Model model) {
		return "index";
	}
	
	@GetMapping("/admin/features")
	public String features(Model model) {
		List<Buffet> elencoBuffet = this.bs.findAllBuffet();
		List<Piatto> elencoPiatti = this.ps.findAllPiatti();
		List<Chef> elencoChef = this.cs.findAllChef();
		List<Ingrediente> elencoIngredienti = this.is.findAllIngredienti();
		model.addAttribute("elencoBuffet", elencoBuffet);
		model.addAttribute("elencoPiatti", elencoPiatti);
		model.addAttribute("elencoChef", elencoChef);
		model.addAttribute("elencoIngredienti", elencoIngredienti);
		return "adminFeatures.html";
	}
	
	
    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public String defaultAfterLogin(Model model) {
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
			return "adminPage.html";
		}
		return "index";
	}
	
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user,
                 BindingResult userBindingResult,
                 @ModelAttribute("credentials") Credentials credentials,
                 BindingResult credentialsBindingResult,
                 Model model) {

        // validate user and credentials fields
        this.userValidator.validate(user, userBindingResult);
        this.credentialsValidator.validate(credentials, credentialsBindingResult);

        // if neither of them had invalid contents, store the User and the Credentials into the DB
        if(!userBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
            // set the user and store the credentials;
            // this also stores the User, thanks to Cascade.ALL policy
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            return "registrationSuccessful.html";
        }
        return "accessoAmministratore.html";
    }
}
