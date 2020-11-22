package etapa4;

import java.util.LinkedList;
import java.util.List;

import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa3.Metodo;
import etapa3.Var;
import etapa3.Variable;

public class Bloque {
	private Token token;
	private List<Sentencia> sentencias;
	private List<Variable> listaVarDeclaradas;
	private List<Bloque> bloques; //Un bloque puede contener bloques

	public Bloque(Token token) {
		sentencias = new LinkedList<Sentencia>();
		listaVarDeclaradas = new LinkedList<Variable>();
		this.token=token;
	
	}
	
	
	
	public void addSent(Sentencia sent) {
		sentencias.add(sent);

	}
	
	public void addVar(Variable var){
		listaVarDeclaradas.add(var);
	}
	public void addBloque(Bloque b){
		this.bloques.add(b);
	}
	
	public int getLine(){
		return token.getNroLinea();
	}

	
	
	/*
	public boolean check(Metodo metodo) throws ErrorSemantico {
		boolean hayReturn = false;

		for (NodoSentencia s : sentencias) {
			// como habia un return, entonces esta sentencia y las siguientes
			// son inalcanzables.!!
			if (hayReturn)
				throw new ErrorSemantico("Codigo inalcanzable.");

			hayReturn = s.check(metodo); 
			// metodo actual , clase actual y calificador (metodo q va cambiando)
		}

		return hayReturn;
	}
	*/


}