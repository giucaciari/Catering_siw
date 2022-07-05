package com.example.demo.model; 
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Piatto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	
	//senza cascade all dà problemi nella creazione dell'oggetto stesso
	@OneToMany( cascade = {CascadeType.ALL}, mappedBy ="piatto")
	private List<Ingrediente> ingredientiDelPiatto;
	
	private String nome;
	
	private String descr;
	
	@ManyToOne
	private Buffet buffet;
	
	
	public Buffet getBuffet() {
		return buffet;
	}
	public void setBuffet(Buffet buffet) {
		this.buffet = buffet;
	}
	public Long getId() {
		return Id;
	}
	public List<Ingrediente> getIngredientiDelPiatto() {
		return ingredientiDelPiatto;
	}
	public void setIngredientiDelPiatto(List<Ingrediente> ingredientiDelPiatto) {
		this.ingredientiDelPiatto = ingredientiDelPiatto;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}

}
