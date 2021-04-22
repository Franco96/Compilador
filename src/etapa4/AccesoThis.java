package etapa4;

import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.Tipo;
import etapa3.TipoClase;
import etapa5.Generador;

public class AccesoThis extends Acceso{
	

	public AccesoThis(Token t) {
		super(t);
	}

	@Override
	public Tipo chequear(Clase clase, Metodo metodo) throws ErrorSemantico {
		
		if(metodo.isStatic())
			throw new ErrorSemantico(t.getNroLinea()+" : no se puede acceder a this en un metodo estatico"
					+"\n\n[Error:"+t.getLexema()+"|"+
					t.getNroLinea()+"]");
		
		
		
		if(this.encadenado!=null){
			
				encadenado.setSeguidoDeThis(true);
			
				return encadenado.chequear(clase,metodo);
		}else
				return new TipoClase(new Token("idClase",clase.getNombre() , t.getNroLinea()));
		
		
		
		
	}

	@Override
	public void generarCodigo() {
		
		
		// Caergo la tercer componente del RA actual en la pila.
				Generador.getGenerador().gen("LOAD 3", "# Cargo referencia al CIR de this (de rutina activa) en el tope de la pila");
				
		if(this.encadenado!=null){
				if(this.esLadoIzquierdo)
					encadenado.setLadoIzquierdo(true);
			encadenado.generarCodigo();
		}
		
		
	}

	
}
