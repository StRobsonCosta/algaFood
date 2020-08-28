package com.algaworks.algafood.di.notificacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;

//@Qualifier("Urgente")   ... desambiguação de beans 
//@Primary
//@Profile("prod")
@TipoDoNotificador(NivelUrgencia.URGENTE)
@Component
public class NotificadorEmail implements Notificador{
	
	//@Value("${notificador.email.host-servidor}")
	//private String host;
	
	//@Value("${notificador.email.porta-servidor}")
	//private Integer porta;
	
	@Autowired
	private NotificadorProperties properties;
	
	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.println("Host: " + properties.getHostServidor());
		System.out.println("Porta: " + properties.getPortaServidor());

		System.out.printf("Notificando %s através do email %s: %s\n",
				cliente.getNome(), cliente.getEmail(), mensagem);
	}



}
