package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import it.uniroma3.siw.controller.validator.BuffetValidator;
import it.uniroma3.siw.service.BuffetService;

@Controller
public class BuffetController {
 @Autowired BuffetService buffetService;
 @Autowired BuffetValidator buffetValidator;
}
