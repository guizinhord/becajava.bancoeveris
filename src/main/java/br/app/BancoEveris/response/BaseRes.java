package br.app.BancoEveris.response;

import javax.persistence.Transient;

public class BaseRes {
	@Transient
	public int StatusCode;
	@Transient
	public String Message;
}
