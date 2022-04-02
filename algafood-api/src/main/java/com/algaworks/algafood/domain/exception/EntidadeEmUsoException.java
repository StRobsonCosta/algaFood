package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.CONFLICT)
public class EntidadeEmUsoException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public EntidadeEmUsoException (String mensagem) {
		super (mensagem);
	}
	
	public EntidadeEmUsoException (Long entidadeId) {
		this(String.format("Entidade de código %d não pode ser removida, pois está em uso", entidadeId));
	}
}
