package br.app.BancoEveris.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.app.BancoEveris.response.BaseRes;

@Entity
public class Operacao extends BaseRes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String tipo;
	private double valor;

	@ManyToOne
	@JoinColumn(name = "IdOrigem")
	private Conta contaOrigem;

	@ManyToOne
	@JoinColumn(name = "IdDestino")
	private Conta contaDestino;

	public Conta getContaOrigem() {
		return contaOrigem;
	}

	public void setContaOrigem(Conta contaOrigem) {
		this.contaOrigem = contaOrigem;
	}

	public Conta getContaDestino() {
		return contaDestino;
	}

	public void setContaDestino(Conta contaDestino) {
		this.contaDestino = contaDestino;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}
