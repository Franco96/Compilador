package etapa4;

import java.util.LinkedList;
import java.util.List;

import etapa1.Token;

public class AccesoMetodo extends Acceso{
	
	private List<Expresion> argumentos;

	public AccesoMetodo(Token t) {
		super(t);
		this.argumentos = new LinkedList<Expresion>();
	}
	
	
	public void addArgumentos(Expresion sent) {
		this.argumentos.add(sent);

	}

}
