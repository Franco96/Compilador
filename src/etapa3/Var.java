package etapa3;

import etapa1.Token;

public abstract class Var {
	protected Tipo tipo;
	protected Token k;
	
	protected int offset;


	protected Var(Token k, Tipo tipo) {
		this.k = k;
		this.tipo = tipo;
		
		offset = -1;  //offset por defecto sin asignar
	}

	public Tipo getTipo() {
		return tipo;
	}

	public String getNombre() {
		return k.getLexema();
	}
	
	public int getLinea(){
		return k.getNroLinea();
	}

	
	
	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public boolean isOffsetAsigned(){
		
		return offset != -1;
	}
	

}
