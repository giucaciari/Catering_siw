package it.uniroma3.siw.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.service.ChefService;

@Component
public class ChefValidator implements Validator {
	@Autowired
	private ChefService chefService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object o, Errors errors) {
		if (this.chefService.alreadyExists((Chef)o)) {
			errors.reject("piatto.duplicato");
		}
		
	}
	
}
