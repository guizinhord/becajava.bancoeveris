package br.app.BancoEveris.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.app.BancoEveris.model.Conta;
import br.app.BancoEveris.request.ContaReq;
import br.app.BancoEveris.response.BaseRes;
import br.app.BancoEveris.response.ContaListRes;
import br.app.BancoEveris.response.ContaRes;
import br.app.BancoEveris.service.ContaService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/contas")
public class ContaController extends BaseController {
	@Autowired
	private ContaService service;

	@PostMapping
	public ResponseEntity inserir(@RequestBody ContaReq requestConta) {
		try {
			BaseRes response = service.inserir(requestConta);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}

	//get por hash
	@GetMapping(path = "/{id}")
	public ResponseEntity obter(@PathVariable Long id) {
		try {

			Conta response = service.obter(id);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}

	@GetMapping
	public ResponseEntity listar() {
		try {
			ContaListRes contas = service.listar();
			return ResponseEntity.status(HttpStatus.OK).body(contas);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity atualizar(@RequestBody ContaReq requestConta, @PathVariable Long id) {
		try {
			BaseRes response = service.atualizar(id, requestConta);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity deletar(@PathVariable Long id) {
		try {
			BaseRes response = service.deletar(id);
			return ResponseEntity.status(response.StatusCode).build();
		} catch (Exception e) {
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}
	
	@GetMapping(path = "/saldo/{hash}")
	public ResponseEntity Saldo(@PathVariable String hash) {
		try {
			ContaRes response = service.Saldo(hash);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			
			return ResponseEntity.status(errorBase.StatusCode).body(errorBase);
		}
	}

}
