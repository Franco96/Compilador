package etapa4;

import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa3.Atributo;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.Parametro;
import etapa3.TablaDeSimbolos;
import etapa3.Tipo;
import etapa3.Variable;

public class AccesoVar extends Acceso{
	


	public AccesoVar(Token t) {
		super(t);
	}

	@Override
	public Tipo chequear(Clase clase,Metodo metodo) throws ErrorSemantico {
			
		Parametro param = metodo.getParametros().get(t.getLexema());
				
		Variable variable = metodo.getVariablesLocales().get(t.getLexema());
		
		Atributo atrib = clase.getAtributos().get(t.getLexema());
		
		
		if(param==null && variable==null && atrib == null)
			
			throw new ErrorSemantico(t.getNroLinea()+" : no existe atributo con el nombre \""+t.getLexema()+"\""+" "
					+ "definido en la clase \""+clase.getNombre()+"\""
					+" o variable/parametro definido en el metodo \""+metodo.getNombre()+"\""
					+"\n\n[Error:"+t.getLexema()+"|"+
					t.getNroLinea()+"]");
		
		Clase claseRetorno;
		
		if(param!=null){
					
						if(encadenado!=null){

									claseRetorno = TablaDeSimbolos.getTablaDeSimbolos().getClases().get(param.getTipo().getNombre());
									if(claseRetorno!=null)
											return encadenado.chequear(claseRetorno,metodo);
									else
											throw new ErrorSemantico(t.getNroLinea()+" : el parametro \""+param.getNombre()+"\""+" del metodo \""+metodo.getNombre()+"\""
													+ " deberia retornar una clase que contenga el metodo o variable \""+encadenado.getNombre()+"\""
													+"\n\n[Error:"+t.getLexema()+"|"+
													t.getNroLinea()+"]");		
												
						}else
							 return param.getTipo();
		
		}else if(variable!=null){
			
						if(encadenado!=null){

									claseRetorno = TablaDeSimbolos.getTablaDeSimbolos().getClases().get(variable.getTipo().getNombre());
				
									if(claseRetorno!=null)
											return encadenado.chequear(claseRetorno,metodo);
									else
											throw new ErrorSemantico(t.getNroLinea()+" : la variable \""+variable.getNombre()+"\""+" del metodo \""+metodo.getNombre()+"\""
													+ " deberia retornar una clase que contenga el metodo o variable \""+encadenado.getNombre()+"\""
													+"\n\n[Error:"+t.getLexema()+"|"+
													t.getNroLinea()+"]");			
							
						}else
							 return variable.getTipo();
		
		}else {
			
						if(encadenado!=null){
				
									claseRetorno = TablaDeSimbolos.getTablaDeSimbolos().getClases().get(atrib.getTipo().getNombre());
			
									if(claseRetorno!=null)
											return encadenado.chequear(claseRetorno,metodo);
									else
						
											throw new ErrorSemantico(t.getNroLinea()+" : el atributo de clase \""+atrib.getNombre()+"\""+" "
													+ "deberia retornar una clase que contenga el metodo o variable \""+encadenado.getNombre()+"\""
													+"\n\n[Error:"+t.getLexema()+"|"+
													t.getNroLinea()+"]");
						}else
							 return atrib.getTipo();
		
		}
		
	}
	
	
	
	
	

}
