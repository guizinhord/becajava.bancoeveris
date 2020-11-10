package br.app.BancoEveris.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.app.BancoEveris.model.Conta;
import br.app.BancoEveris.model.Operacao;
import br.app.BancoEveris.repository.ContaRepository;
import br.app.BancoEveris.repository.OperacaoRepository;
import br.app.BancoEveris.request.ContaReq;
import br.app.BancoEveris.request.OperacaoReq;
import br.app.BancoEveris.request.TranferenciaReq;
import br.app.BancoEveris.response.BaseRes;

@Service
public class OperacaoService {
	@Autowired
	private OperacaoRepository repository;

	@Autowired
	private ContaRepository repositoryConta;

	public BaseRes depositar(OperacaoReq request) {
		Optional<Conta> conta = repositoryConta.findByHash(request.getHash());

		Operacao op = new Operacao();
		BaseRes base = new BaseRes();
		base.StatusCode = 400;

		if (!conta.isPresent()) {
			base.Message = "Conta não encontrada";
			return base;
		}

		if (request.getTipo() == "") {
			base.Message = "Insira o tipo D (deposito)";
			return base;
		}

		if (request.getValor() <= 0) {
			base.Message = "O Valor do cliente não foi preenchido.";
			return base;
		}

		op.setTipo("D");
		op.setValor(request.getValor());
		op.setContaOrigem(conta.get());

		conta.get().setSaldo(conta.get().getSaldo() + request.getValor());

		repositoryConta.save(conta.get());
		repository.save(op);
		base.StatusCode = 200;
		base.Message = "Deposito realizado com sucesso.";
		return base;
	}

	public BaseRes sacar(OperacaoReq request) {
		Optional<Conta> conta = repositoryConta.findByHash(request.getHash());

		Operacao op = new Operacao();
		BaseRes base = new BaseRes();
		if (!conta.isPresent()) {
			base.Message = "Conta não encontrada";
			return base;
		}

		if (request.getValor() > conta.get().getSaldo()) {
			base.Message = "Saque nao pode ser efetuado  valor do saldo menor ";
			return base;
		}

		op.setValor(request.getValor());
		op.setTipo("S");
		op.setContaOrigem(conta.get());

		conta.get().setSaldo(conta.get().getSaldo() - request.getValor());

		repositoryConta.save(conta.get());
		repository.save(op);

		base.StatusCode = 200;
		base.Message = "Saque realizado com sucesso.";
		return base;
	}

	public BaseRes transferir(TranferenciaReq request) {
		Optional<Conta> conta1 = repositoryConta.findByHash(request.getHashOrigem());
		Optional<Conta> conta2 = repositoryConta.findByHash(request.getHashDestino());

		Operacao op = new Operacao();
		BaseRes base = new BaseRes();
		Operacao operacao = new Operacao();

		if (!conta1.isPresent()) {
			base.Message = "Conta não confere";
			return base;
		}

		if (!conta2.isPresent()) {
			base.Message = "Conta não confere";
			return base;
		}

		if (request.getValor() == 0) {
			base.Message = "O valor Esta abaixo do limite Tente novamente ";
			return base;
		}

		if (request.getValor() > conta1.get().getSaldo()) {
			base.Message = "O valor Inserido esta Abaixo do seu Saldo Tente Novamente";
			return base;
		}

		conta1.get().setSaldo(conta1.get().getSaldo() - request.getValor());
		conta2.get().setSaldo(conta2.get().getSaldo() + request.getValor());

		op.setTipo("T");
		operacao.setContaOrigem(conta1.get());
		operacao.setContaDestino(conta2.get());
		operacao.setValor(request.getValor());

		repositoryConta.save(conta1.get());
		repositoryConta.save(conta2.get());

		repository.save(operacao);

		base.StatusCode = 200;
		base.Message = "Transferencia realizada com sucesso.";
		return base;
	}

	public double Saldo(Long contaId) {

		double saldo = 0;

		Conta contaOrigem = new Conta();
		contaOrigem.setId(contaId);

		Conta contaDestino = new Conta();
		contaDestino.setId(contaId);

		List<Operacao> lista = repository.findOperacoesPorConta(contaId);

		for (Operacao o : lista) {
			switch (o.getTipo()) {
			case "D":
				saldo += o.getValor();
				break;
			case "S":
				saldo -= o.getValor();
				break;
			case "T":

				if (o.getContaDestino().getId() == contaId) {

					saldo -= o.getValor();
				}
				if (o.getContaOrigem().getId() == contaId) {
					saldo += o.getValor();
				}
				break;
			default:
				break;
			}
		}

		return saldo;
	}

}
