package etapa4;

import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa3.Atributo;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.Parametro;
import etapa3.TablaDeSimbolos;
import etapa3.Tipo;
import etapa3.Var;
import etapa3.Variable;
import etapa5.Generador;

public class AccesoVar extends Acceso{
	
	Bloque bloque;
	
	
	private Var varAsociada;
	

	public AccesoVar(Token t,Bloque bloque) {
		super(t);
		this.bloque = bloque;
	}

	@Override
	public Tipo chequear(Clase clase,Metodo metodo) throws ErrorSemantico {
			
		Parametro param = metodo.getParametros().get(t.getLexema());
				
		Variable variable = bloque.getVariablesLocales().get(t.getLexema());
		
		Atributo atrib = clase.getAtributos().get(t.getLexema());
		
		
		if(param==null && variable==null && atrib == null)
			
			throw new ErrorSemantico(t.getNroLinea()+" : no existe atributo con el nombre \""+t.getLexema()+"\""+" "
					+ "definido en la clase \""+clase.getNombre()+"\""
					+" o variable/parametro definido en el metodo \""+metodo.getNombre()+"\""
					+"\n\n[Error:"+t.getLexema()+"|"+
					t.getNroLinea()+"]");
		
		Clase claseRetorno;
		
		if(param!=null){
			
						varAsociada = param;
					
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
			
						varAsociada = variable;
			
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
						
						varAsociada = atrib;
						
						if(metodo.isStatic())
							throw new ErrorSemantico(t.getNroLinea()+" : no se puede acceder a un atributo en un metodo estatico"
									+"\n\n[Error:"+t.getLexema()+"|"+
									t.getNroLinea()+"]");
			
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

	@Override
	public void generarCodigo() {
		
		
		
		
		// IMPORTANTE: inicialmente, existe un valor en el tope de la pila.
		// Si el acceso requerido se aplica sobre un atributo, requiero acceder al CIR de this.
		
		
		if (varAsociada instanceof Atributo) {
			
			// Cargo el this correspondiente al RA de la rutina activa en el tope de la pila
			Generador.getGenerador().gen("LOAD 3", "# Cargo referencia al CIR de this (de rutina activa) en el tope de la pila");
			
			if(!esLadoIzquierdo)
				// Apilo el valor del atributo en el CIR de this, con offset asociado 'offsetDeVariable'.
				Generador.getGenerador().gen("LOADREF " + varAsociada.getOffset(), "# Cargo el valor del atributo en el tope de la pila");
		
			else{
				// Aplico un swap, quedando el valor correspondiente al atributo accedido en el tope de la pila, encima de this.
				Generador.getGenerador().gen("SWAP", "# Intercambio el valor del parámetro actual por el de this en el tope de la pila");
				Generador.getGenerador().gen("STOREREF " + varAsociada.getOffset(), "# Almaceno el valor del tope de la pila en el atributo");
			}
			
		}
		// Si el acceso se aplica sobre un parámetro formal o una variable local no necesito this y el acceso es similar en ambos casos.
		else{ 
				if(!esLadoIzquierdo)
					Generador.getGenerador().gen("LOAD " + varAsociada.getOffset(), "# Cargo el valor de variable/parámetro en el tope de la pila");
				else 
					Generador.getGenerador().gen("STORE " + varAsociada.getOffset(), "# Almaceno el valor del tope de la pila en variable/parámetro");
		
		}
		
		
		
		if(this.encadenado!=null){
			if(this.esLadoIzquierdo)
				encadenado.setLadoIzquierdo(true);
			encadenado.generarCodigo();
		}
	
	}
	

	

}
