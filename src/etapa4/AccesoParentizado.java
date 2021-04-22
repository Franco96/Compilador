package etapa4;

import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.TablaDeSimbolos;
import etapa3.Tipo;
import etapa3.TipoClase;

public class AccesoParentizado extends Acceso{
	
	private Expresion expresion;

	public AccesoParentizado(Token t, Expresion expresion) {
		super(t);
		this.expresion = expresion;
	}

	
	@Override
	public Tipo chequear(Clase clase, Metodo metodo) throws ErrorSemantico {
		
		
		Tipo tipoDelParentesis = expresion.chequear(clase, metodo);
		
	
		if(this.encadenado!=null){
			
			if(!(tipoDelParentesis instanceof TipoClase)){
				throw new ErrorSemantico(t.getNroLinea()+" : la expresion parentizada deberia retornar una clase "
						+ "que contenga el metodo/variable \""+encadenado.getNombre()+"\""
						+"\n\n[Error:"+encadenado.getNombre()+"|"+
						t.getNroLinea()+"]");
			}
			
			Clase c = TablaDeSimbolos.getTablaDeSimbolos().getClases().get(tipoDelParentesis.getNombre());
			
			return encadenado.chequear(c, metodo);
		}
		else
			return tipoDelParentesis;
			
			
	}


	@Override
	public void generarCodigo() {
		
		expresion.generarCodigo();
		
		if(this.encadenado!=null){
			if(this.esLadoIzquierdo)
				encadenado.setLadoIzquierdo(true);
			encadenado.generarCodigo();
		}
		
	}

	
}
