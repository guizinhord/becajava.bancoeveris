package br.app.BancoEveris.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.app.BancoEveris.request.OperacaoReq;
import br.app.BancoEveris.request.TranferenciaReq;
import br.app.BancoEveris.response.BaseRes;
import br.app.BancoEveris.service.OperacaoService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/operacao")
public class OperacaoController {
	@Autowired
	private OperacaoService service;

	@PostMapping(path = "/depositos")
	public ResponseEntity depositar(@RequestBody OperacaoReq requestConta) {
		try {
			BaseRes response = service.depositar(requestConta);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
		}
	}

	@PostMapping(path = "/saques")
	public ResponseEntity sacar(@RequestBody OperacaoReq requestConta) {
		try {
			BaseRes response = service.sacar(requestConta);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
		}
	}

	@PostMapping(path = "/transferencia")
	public ResponseEntity transferir(@RequestBody TranferenciaReq requestTransferencia) {
		try {
			BaseRes response = service.transferir(requestTransferencia);
			return ResponseEntity.status(response.StatusCode).body(response);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");
		}
	}

}
