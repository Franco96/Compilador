package etapa4;

import Excepciones.ErrorSemantico;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.Tipo;
import etapa3.TipoBoolean;
import etapa3.TipoInt;

public class ExpresionUnaria extends Expresion{

	private Operando operando;
	
	
	public ExpresionUnaria(Operando operando){
		
		this.operando = operando;
	}

	
	public int getLinea(){
		
		return operando.getLinea();
	}
	
	public String getNombre(){
		
		return operando.getNombre();
	}

	@Override
	public Tipo chequear(Clase clase, Metodo metodo) throws ErrorSemantico {
		
		Tipo tipoOperando = operando.chequear(clase, metodo);
		
		if(operador!=null){
		
						switch (operador.getLexema()) {
		
							case "-":
							case "+":
										if(tipoOperando instanceof TipoInt) 
												return new TipoInt(tipoOperando.getLinea());
										else
												throw new ErrorSemantico(operando.getLinea()+" : el tipo del operando debe ser de tipo entero \""+operando.getNombre()+"\""
															+"\n\n[Error:"+operando.getNombre()+"|"+
															operando.getLinea()+"]");
			
							case "!":
										if(tipoOperando instanceof TipoBoolean) 
												return new TipoBoolean(tipoOperando.getLinea());
										else
												throw new ErrorSemantico(operando.getLinea()+" : el tipo del operando debe ser de tipo boolean \""+operando.getNombre()+"\""
															+"\n\n[Error:"+operando.getNombre()+"|"+
															operando.getLinea()+"]");	
						}
						
						return null;
		
		}else{
			
			return tipoOperando;
		}
	
	}
	
	
	
	
}
