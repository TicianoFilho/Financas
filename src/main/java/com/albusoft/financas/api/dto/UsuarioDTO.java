package com.albusoft.financas.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDTO {

	public String nome;
	public String email;
	public String senha;
	
	
}
