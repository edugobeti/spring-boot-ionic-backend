package com.edugobeti.cursomc.domain.enuns;

public enum EstadoPagamento {
	
	PENDENTE(1, "Pendente"),
	QUITADO(2, "Quitado"),
	CANCELADO(3, "Cancelado");
	
	private int code;
	private String tipo;
	
	private EstadoPagamento(Integer cod, String tipo) {
		this.code = cod;
		this.tipo = tipo;
	}

	public int getCode() {
		return code;
	}

	public String getTipo() {
		return tipo;
	}

	public static EstadoPagamento toEnum(Integer id) {
		
		if(id == null) {
			return null;
		}
		
		for(EstadoPagamento x : EstadoPagamento.values()) {
			if(id.equals(x.getCode())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido " + id);
	}
	
	
	
}
