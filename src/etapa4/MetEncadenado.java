package etapa4;


import etapa1.Token;

public class MetEncadenado extends Encadenado{

	private AccesoMetodo actMet;
	
	public MetEncadenado(Token token,AccesoMetodo actMet) {
		super(token);
		this.actMet = actMet;
	}

	
	public void setAccesoMetodo(AccesoMetodo actMet){
		this.actMet = actMet;
	}
	
}
