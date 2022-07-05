package it.uniroma3.siw.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

public class Buffet{
		
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;

		private String nome;

		private String descr;

		@ManyToOne
		private Chef chef;

		@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
		@JoinColumn(name = "piatto_id")
		private List<Piatto> piatti;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
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

		public Chef getChef() {
			return chef;
		}

		public void setChef(Chef chef) {
			this.chef = chef;
		}

		public List<Piatto> getPiatti() {
			return piatti;
		}

		public void setPiatti(List<Piatto> piatti) {
			this.piatti = piatti;
		}
}
