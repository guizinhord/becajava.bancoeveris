package br.app.BancoEveris.response;

import java.util.List;

import br.app.BancoEveris.model.Conta;

public class ContaListRes extends BaseRes {

	private List<Conta> Contas;

	public List<Conta> getContas() {
		return Contas;
	}

	public void setContas(List<Conta> contas) {
		Contas = contas;
	}

}
