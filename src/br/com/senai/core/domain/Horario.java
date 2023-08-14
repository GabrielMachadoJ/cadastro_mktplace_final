package br.com.senai.core.domain;

import java.security.Timestamp;
import java.sql.Time;
import java.util.Objects;

public class Horario {
	
	private int id ;
	private String diaDaSemana;
	private Time horaAbertura;
	private Time horaFechamento;
	private Restaurante restaurante;
	
	
	public Horario( String diaDaSemana, Time horaAbertura, Time horaFechamento,Restaurante restaurante) {
		
		this.diaDaSemana = diaDaSemana;
		this.horaAbertura = horaAbertura;
		this.horaFechamento = horaFechamento;
		this.restaurante = restaurante;
	}


	public Horario(int id,String diaDaSemana, Time horaAbertura, Time horaFechamento,Restaurante restaurante) {
		
		this(diaDaSemana,horaAbertura,horaFechamento,restaurante);
		this.id = id;
	
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getDiaDaSemana() {
		return diaDaSemana;
	}


	public void setDiaDaSemana(String diaDaSemana) {
		this.diaDaSemana = diaDaSemana;
	}


	public Time getHoraAbertura() {
		return horaAbertura;
	}


	public void setHoraAbertura(Time horaAbertura) {
		this.horaAbertura = horaAbertura;
	}


	public Time getHoraFechamento() {
		return horaFechamento;
	}


	public void setHoraFechamento(Time horaFechamento) {
		this.horaFechamento = horaFechamento;
	}


	public Restaurante getRestaurante() {
		return restaurante;
	}


	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Horario other = (Horario) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Horario [id=" + id + ", diaDaSemana=" + diaDaSemana + ", horaAbertura=" + horaAbertura
				+ ", horaFechamento=" + horaFechamento + ", restaurante=" + restaurante + "]";
	} 
	
	
	
	
	
	

}
