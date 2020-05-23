package com.edugobeti.cursomc.domain.enuns;

public enum TipoCliente {
	
	PESSOA_FISICA(1, "Pessoa Física"),
	PESSOA_JURIDICA(2, "Pessoa Jurídica");
	
	private int code;
	private String tipo;
	
	private TipoCliente(Integer cod, String tipo) {
		this.code = cod;
		this.tipo = tipo;
	}

	public int getCode() {
		return code;
	}

	public String getTipo() {
		return tipo;
	}

	public static TipoCliente toEnum(Integer id) {
		
		if(id == null) {
			return null;
		}
		
		for(TipoCliente x : TipoCliente.values()) {
			if(id.equals(x.getCode())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido " + id);
	}
	
	

}
