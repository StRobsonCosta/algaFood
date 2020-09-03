package com.algaworks.algafood.api.model;

import java.util.List;

import com.algaworks.algafood.domain.model.Cozinha;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;
import lombok.NonNull;

@JacksonXmlRootElement (localName = "cozinhas")
@Data
public class CozinhasXmlWrapper {
	
	@JsonProperty ("cozinha")
	@JacksonXmlElementWrapper (useWrapping = false)
	@NonNull
	private List<Cozinha> cozinhas;

}
//esta classe é apenas para que o a lista e objetos não chamem-se "list" e "name" quando visto no POST pelo formato XML
