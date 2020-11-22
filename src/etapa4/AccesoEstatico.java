package etapa4;

import etapa1.Token;

public class AccesoEstatico extends Acceso{
	
	
	private AccesoMetodo actMet;

	public AccesoEstatico(Token t) {
		super(t);
		
	}

	public AccesoMetodo getAccesoMet() {
		return actMet;
	}

	public void setAccesoMet(AccesoMetodo actMet) {
		this.actMet = actMet;
	}
	
	
	

}
