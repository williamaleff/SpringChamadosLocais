package br.com.springboot.treinamento_dev.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;


@Entity
@SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1, initialValue = 1)
public class Suporte implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
	private Long id;
	
	private String tipoDeChamado;

	private String descricaoDoChamado;
	
	private String requerente;
	
	private String horarioDeAberturaDoChamado;
	
	private String dataDoChamado;
	
	private String chamadoGlpi;
	
	public String getTipoDeChamado() {
		return tipoDeChamado;
	}

	public void setTipoDeChamado(String tipoDeChamado) {
		this.tipoDeChamado = tipoDeChamado;
	}
	
	public String getChamadoGlpi() {
		return chamadoGlpi;
	}

	public void setChamadoGlpi(String chamadoGlpi) {
		this.chamadoGlpi = chamadoGlpi;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricaoDoChamado() {
		return descricaoDoChamado;
	}

	public void setDescricaoDoChamado(String descricaoDoChamado) {
		this.descricaoDoChamado = descricaoDoChamado;
	}

	public String getRequerente() {
		return requerente;
	}

	public void setRequerente(String requerente) {
		this.requerente = requerente;
	}

	public String getHorarioDeAberturaDoChamado() {
		return horarioDeAberturaDoChamado;
	}

	public void setHorarioDeAberturaDoChamado(String horarioDeAberturaDoChamado) {
		this.horarioDeAberturaDoChamado = horarioDeAberturaDoChamado;
	}

	public String getDataDoChamado() {
		return dataDoChamado;
	}

	public void setDataDoChamado(String dataDoChamado) {
		this.dataDoChamado = dataDoChamado;
	}
	
	
}
