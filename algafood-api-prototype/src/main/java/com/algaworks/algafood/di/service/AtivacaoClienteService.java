package com.algaworks.algafood.di.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.notificacao.NivelUrgencia;
import com.algaworks.algafood.di.notificacao.Notificador;
import com.algaworks.algafood.di.notificacao.TipoDoNotificador;

@Component
public class AtivacaoClienteService {
	
	//@Qualifier("Urgente")... desambiguação de beans 
	//@TipoDoNotificador(NivelUrgencia.URGENTE)
	//@Autowired
	//private Notificador notificador;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	public void ativar (Cliente cliente) {
		cliente.ativar();
		
		//notificador.notificar(cliente, "Seus cadastro está ativo!");
		
		eventPublisher.publishEvent(new ClienteAtivadoEvent(cliente));
	}
		
}







	
	/*@Autowired
	private List <Notificador> notificadores;
	
	public void ativar(Cliente cliente) {
		cliente.ativar();
		
		for (Notificador notificador: notificadores) {
			notificador.notificar(cliente, "Seu cadastro está ativo!");
		}
		
	}



	

}*/
