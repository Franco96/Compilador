package etapa4;

import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.Tipo;
import etapa3.TipoInt;

public class SentenciaAsignacion extends Sentencia{

	private Token tipoAsignacio;
	private Acceso ladoIzq;
	private Expresion ladoDer;
	
	
	
	public SentenciaAsignacion(Token tipoAsignacio, Acceso ladoIzq, Expresion ladoDer) {
		this.tipoAsignacio = tipoAsignacio;
		this.ladoIzq = ladoIzq;
		this.ladoDer = ladoDer;
	}



	@Override
	public void controlSentencia(Clase clase, Metodo metodo) throws ErrorSemantico {
		
		if(!ladoIzq.tieneEncadenado()){
			
			if(!(ladoIzq instanceof AccesoVar))
				
				throw new ErrorSemantico(ladoIzq.getLinea()+" : el lado izquierdo de la asignacion \""+ladoIzq.getNombre()+"\""
						+ " debe ser una variable"
						+"\n\n[Error:"+ladoIzq.getNombre()+"|"+
						ladoIzq.getLinea()+"]");
			
		
		}else{
			
			if(!(obtenerUltimoEncadenado(ladoIzq.getEncadenado()) instanceof VarEncadenada))
				throw new ErrorSemantico(ladoIzq.getLinea()+" : el ultimo elemento de la llamada encadenada del "
						+ "lado izquierdo de la asignacion \""+ladoIzq.getNombre()+"\""
						+ " debe ser una variable"
						+"\n\n[Error:"+ladoIzq.getNombre()+"|"+
						ladoIzq.getLinea()+"]");
			
		}
		
		Tipo tipoLadoIzq = ladoIzq.chequear(clase, metodo);
		Tipo tipoLadoDer = ladoDer.chequear(clase, metodo);
		
		
		switch (this.tipoAsignacio.getLexema()) {
		
		case "+=":
		case "-=":
					if( !(tipoLadoIzq instanceof TipoInt) || !(tipoLadoDer instanceof TipoInt))
						throw new ErrorSemantico(tipoAsignacio.getNroLinea()+" : el tipo de asinacion \""+this.tipoAsignacio.getLexema()+"\""
								+ " es incorrecta porque el lado derecho o izquierde no es de tipo entero"
								+"\n\n[Error:"+ladoIzq.getNombre()+"|"+
								ladoIzq.getLinea()+"]");	
		
		case "=":
					if(!tipoLadoDer.conforma(tipoLadoIzq))
						throw new ErrorSemantico(tipoAsignacio.getNroLinea()+" : el tipo \""+tipoLadoDer.getNombre()+"\""
								+ " no conforma el tipo \""+tipoLadoIzq.getNombre()+"\""
								+"\n\n[Error:"+ladoIzq.getNombre()+"|"+
								ladoIzq.getLinea()+"]");

		}
		
		
		
		
	}
	
	
	
	private Encadenado obtenerUltimoEncadenado(Encadenado encadenado){
		
		if(encadenado.getSiguiente()==null){
			return encadenado;
		}else{
			return obtenerUltimoEncadenado(encadenado.getSiguiente());
		}
		
	}
	
	
	
}
