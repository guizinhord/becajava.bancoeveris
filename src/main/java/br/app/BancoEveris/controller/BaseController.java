package br.app.BancoEveris.controller;

import br.app.BancoEveris.response.BaseRes;

public class BaseController {
	
	public BaseRes errorBase = new BaseRes();
	public BaseController() {
		errorBase.StatusCode = 500;
		errorBase.Message = "ERRO inesperado. Contate o ADM";
	}
}
