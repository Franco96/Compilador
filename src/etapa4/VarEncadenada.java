package etapa4;

import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa3.Atributo;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.TablaDeSimbolos;
import etapa3.Tipo;

public class VarEncadenada extends Encadenado{

	public VarEncadenada(Token token) {
		super(token);
	}

	@Override
	public Tipo chequear(Clase clase,Metodo metodo) throws ErrorSemantico {
		
		String nombreAtribLlamada = token.getLexema(); 
		int linea = token.getNroLinea();
		Atributo atributoEnca = clase.getAtributos().get(nombreAtribLlamada);
		
		
		if(atributoEnca == null)
			throw new ErrorSemantico(linea+" : no existe un atributo con el nombre \""+nombreAtribLlamada+"\""+" "
					+ "definido en la clase \""+clase.getNombre()+"\""
					+"\n\n[Error:"+nombreAtribLlamada+"|"+
					linea+"]");
		
		
		if(atributoEnca.getVisibilidad().equals("private"))
			throw new ErrorSemantico(linea+" : el atributo con el nombre \""+nombreAtribLlamada+"\""+" "
					+ "definido en la clase \""+clase.getNombre()+"\" debe ser publico"
					+"\n\n[Error:"+nombreAtribLlamada+"|"+
					linea+"]");
		
		
		if(this.siguiente!=null){
			
			Clase claseRetorno = TablaDeSimbolos.getTablaDeSimbolos().getClases().get(atributoEnca.getTipo().getNombre());
			
			if(claseRetorno!=null)
				  return this.siguiente.chequear(claseRetorno,metodo);
			else
					throw new ErrorSemantico(linea+" : la variable \""+nombreAtribLlamada+"\""+" "
							+ "deberia retornar una clase que contenga el metodo o variable \""+siguiente.getNombre()+"\""
							+"\n\n[Error:"+nombreAtribLlamada+"|"+
							linea+"]");
	
		}else
			 return atributoEnca.getTipo();	
		
	}

}
