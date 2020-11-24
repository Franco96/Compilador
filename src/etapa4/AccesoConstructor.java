package etapa4;

import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.TablaDeSimbolos;
import etapa3.Tipo;


public class AccesoConstructor extends AccesoMetodo {

	public AccesoConstructor(Token t) {
		super(t);
		
	}
	
	@Override
	public Tipo chequear(Clase clase,Metodo metodo) throws ErrorSemantico {
		
		Clase claseConstructor = TablaDeSimbolos.getTablaDeSimbolos().getClases().get(t.getLexema());
		
		if(claseConstructor == null)
			throw new ErrorSemantico(t.getNroLinea()+" : acceso a constructor invalido porque la clase \""+t.getLexema()+"\" no existe"
					+"\n\n[Error:"+t.getLexema()+"|"+
					t.getNroLinea()+"]");
		
		
		chequearArgumentos(claseConstructor.getConstructor(), clase , metodo);
		
		if(this.encadenado!=null)		
					return this.encadenado.chequear(claseConstructor,metodo);
		
		else
			 return claseConstructor.getConstructor().getTipoRetorno();
		
	}

}
