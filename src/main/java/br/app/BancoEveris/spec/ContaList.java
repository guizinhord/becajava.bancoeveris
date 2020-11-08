package br.app.BancoEveris.spec;

import java.util.List;

import br.app.BancoEveris.model.BaseResponse;
import br.app.BancoEveris.model.Conta;

public class ContaList extends BaseResponse {

	private List<Conta> Contas;

	public List<Conta> getContas() {
		return Contas;
	}

	public void setContas(List<Conta> contas) {
		Contas = contas;
	}

}
