package it.uniroma3.siw.controller.validator;

import org.springframework.validation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.service.BuffetService;

@Component
public class BuffetValidator implements Validator {
	@Autowired
	private BuffetService buffetService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object o, Errors errors) {
		if (this.buffetService.alreadyExists((Buffet)o)) {
			errors.reject("piatto.duplicato");
		}
		
	}

}
