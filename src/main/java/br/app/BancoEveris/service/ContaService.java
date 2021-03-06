
package br.app.BancoEveris.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.app.BancoEveris.model.Conta;
import br.app.BancoEveris.model.Operacao;
import br.app.BancoEveris.repository.ContaRepository;
import br.app.BancoEveris.repository.OperacaoRepository;
import br.app.BancoEveris.request.ContaReq;
import br.app.BancoEveris.response.BaseRes;
import br.app.BancoEveris.response.ContaListRes;
import br.app.BancoEveris.response.ContaRes;

@Service
public class ContaService {
	@Autowired
	private ContaRepository repository;
	
	@Autowired
	private OperacaoService operacaoService;
	
	public BaseRes inserir(ContaReq request) {
		Conta conta = new Conta();
		BaseRes base = new BaseRes();
		base.StatusCode = 400;

		if (request.getNome() == "") {
			base.Message = "O nome do cliente não foi preenchido.";
			return base;
		}

		if (request.getCpf() == "") {
			base.Message = "O CPF do cliente não foi preenchido.";
			return base;
		}
		UUID uuid = UUID.randomUUID();
		conta.setHash(uuid.toString());

		conta.setSaldo(0.0);
		
		conta.setNome(request.getNome());
		conta.setCpf(request.getCpf());
		
		repository.save(conta);
		base.StatusCode = 201;
		base.Message = "Cliente inserida com sucesso.";
		return base;
	}

	public Conta obter(Long id) {
		Optional<Conta> cliente = repository.findById(id);

		Conta response = new Conta();
		
		if (cliente.isEmpty()) {
			response.Message = "Cliente não encontrado";
			response.StatusCode = 404;
			return response;
		}
		
		response.setHash(cliente.get().getHash());
		response.setSaldo(cliente.get().getSaldo());
		response.Message = "Cliente obtido com sucesso";
		response.StatusCode = 200;
		return response;
	}

	public ContaListRes listar() {

		List<Conta> lista = repository.findAll();

		ContaListRes response = new ContaListRes();
		response.setContas(lista);
		response.StatusCode = 200;
		response.Message = "Clientes obtidos com sucesso.";

		return response;
	}

	public BaseRes atualizar(Long id, ContaReq request) {
		Conta conta = new Conta();
		BaseRes base = new BaseRes();
		base.StatusCode = 400;

		if (request.getNome() == "") {
			base.Message = "O nome do cliente não foi preenchido.";
			return base;
		}

		if (request.getCpf() == "") {
			base.Message = "O CPF do cliente não foi preenchido.";
			return base;
		}

		if (request.getHash() == "") {
			base.Message = "O Hash do cliente não foi preenchido.";
			return base;
		}

		conta.setId(id);
		conta.setNome(request.getNome());
		conta.setCpf(request.getCpf());
		conta.setHash(request.getHash());

		repository.save(conta);
		base.StatusCode = 200;
		base.Message = "Cliente atualizado com sucesso.";
		return base;
	}

	public BaseRes deletar(Long id) {
		BaseRes response = new BaseRes();

		if (id == null || id == 0) {
			response.StatusCode = 400;
			return response;
		}

		repository.deleteById(id);
		response.StatusCode = 200;
		return response;
	}
	
	public ContaRes Saldo(String hash) {

		ContaRes response = new ContaRes();
		response.StatusCode = 400;

		Optional<Conta> conta = repository.findByHash(hash);

		if (conta == null) {
			response.Message = "Conta não encontrada!!";
			return response;
		}

		double saldo = operacaoService.Saldo(conta.get().getId());

		response.setSaldo(saldo);
		response.setNome(conta.get().getNome());
		
		response.setHash(conta.get().getHash());
		response.StatusCode = 200;
		return response;
	}

}
