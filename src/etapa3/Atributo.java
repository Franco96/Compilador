package etapa3;

import etapa1.Token;


public class Atributo extends Var{

	protected String visibilidad;


	public Atributo(Token tok,  String visibilidad, Tipo tipo) {
		super(tok,tipo);
		this.visibilidad = visibilidad;
	
	}


	public String getVisibilidad() {
		return visibilidad;
	}




	
	
}
