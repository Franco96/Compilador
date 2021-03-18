package etapa4;

import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.Tipo;
import etapa3.TipoBoolean;
import etapa3.TipoInt;
import etapa5.Generador;

public class ExpresionBinaria extends Expresion{

private Expresion ladoIzquierdo;
private Expresion ladoDerecho;


public ExpresionBinaria(Expresion ladoIzquierdo, Expresion ladoDerecho,Token operador) {
	
	this.ladoIzquierdo = ladoIzquierdo;
	this.ladoDerecho = ladoDerecho;
	this.operador = operador;
}


@Override
public Tipo chequear(Clase clase, Metodo metodo) throws ErrorSemantico {
	
	Tipo tipoIzq = ladoIzquierdo.chequear(clase, metodo);
	Tipo tipoDer = ladoDerecho.chequear(clase, metodo);
	
	switch (operador.getLexema()) {
	case "+":
	case "-":
	case "*":
	case "/":
	case "%":
			if (tipoIzq instanceof TipoInt && tipoDer instanceof TipoInt)
				return new TipoInt(operador.getNroLinea());
			else
				throw new ErrorSemantico(operador.getNroLinea()+" : el operador \""+operador.getLexema()+"\" solo trabaja con expresiones de tipo entero"
						+"\n\n[Error:"+operador.getLexema()+"|"+
						operador.getNroLinea()+"]");
	case "&&":
	case "||":
			if (tipoIzq instanceof TipoBoolean && tipoDer instanceof TipoBoolean) 
				return new TipoBoolean(operador.getNroLinea());
			else
				throw new ErrorSemantico(operador.getNroLinea()+" : el operador \""+operador.getLexema()+"\" solo trabaja con expresiones de tipo boolean"
						+"\n\n[Error:"+operador.getLexema()+"|"+
						operador.getNroLinea()+"]");
	case ">":
	case "<":
	case ">=":
	case "<=":
			if (tipoIzq instanceof TipoInt && tipoDer instanceof TipoInt)
				return new TipoBoolean(operador.getNroLinea());
			else
				throw new ErrorSemantico(operador.getNroLinea()+" : el operador \""+operador.getLexema()+"\" solo trabaja con expresiones de tipo entero"
						+"\n\n[Error:"+operador.getLexema()+"|"+
						operador.getNroLinea()+"]");
	case "==":
	case "!=":
		if (tipoIzq.conforma(tipoDer) || tipoDer.conforma(tipoIzq)) 
			return new TipoBoolean(operador.getNroLinea());
		else
			throw new ErrorSemantico(operador.getNroLinea()+" : el operador \""+operador.getLexema()+"\" no conforma los tipos"
					+"\n\n[Error:"+operador.getLexema()+"|"+
					operador.getNroLinea()+"]");
	}
	
	return null;
}


@Override
public void generarCodigo() {
	
	ladoIzquierdo.generarCodigo();
	
	ladoDerecho.generarCodigo();
			
	
	switch (operador.getLexema()) {
	
		case "+":
				Generador.getGenerador().gen("ADD","");
				break;
		case "-":
				Generador.getGenerador().gen("SUB","");
				break;
		case "*":
				Generador.getGenerador().gen("MUL","");
				break;
		case "/":
				Generador.getGenerador().gen("DIV", "# Division");
				break;
		case "%":
				Generador.getGenerador().gen("MOD", "# Modulo");
				break;
		case "&&":
				Generador.getGenerador().gen("AND","");
				break;
		case "||":
				Generador.getGenerador().gen("OR","");
				break;
		case ">":
				Generador.getGenerador().gen("GT", "# Mayor");
				break;
		case "<":
				Generador.getGenerador().gen("LT", "# Menor");
				break;
		case ">=":
				Generador.getGenerador().gen("GE", "# Mayor o igual");
				break;
		case "<=":
				Generador.getGenerador().gen("LE", "# Menor o igual");
				break;
		case "==":
				Generador.getGenerador().gen("EQ", "# Igualdad \"==\"");
				break;
		case "!=":
				Generador.getGenerador().gen("NE", "# Desiguialdad \"!=\" ");
				break;
	
	
	}
	
}
	



	
}
