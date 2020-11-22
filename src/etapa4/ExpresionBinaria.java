package etapa4;

import etapa1.Token;

public class ExpresionBinaria extends Expresion{

private Expresion ladoIzquierdo;
private Expresion ladoDerecho;


public ExpresionBinaria(Expresion ladoIzquierdo, Expresion ladoDerecho,Token operador) {
	
	this.ladoIzquierdo = ladoIzquierdo;
	this.ladoDerecho = ladoDerecho;
	this.operador = operador;
}
	



	
}
