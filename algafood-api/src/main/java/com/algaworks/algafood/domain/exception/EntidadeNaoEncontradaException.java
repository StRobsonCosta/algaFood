package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

//public class EntidadeNaoEncontradaException extends ResponseStatusException{
//	private static final long serialVersionUID = 1L;
//	
//	public EntidadeNaoEncontradaException (HttpStatus status, String mensagem) {
//		super(status, mensagem);
//	}
//	
//	public EntidadeNaoEncontradaException (String mensagem) {
//		this(HttpStatus.NOT_FOUND, mensagem);
//	}
//}

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public abstract class EntidadeNaoEncontradaException extends NegocioException{
	
	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncontradaException (String mensagem) {
		super(mensagem);
	}

}