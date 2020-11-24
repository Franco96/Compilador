package etapa4;

import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.TablaDeSimbolos;
import etapa3.Tipo;

public class AccesoEstatico extends Acceso{
	
	
	private AccesoMetodo actMet;

	public AccesoEstatico(Token t) {
		super(t);
		
	}

	public AccesoMetodo getAccesoMet() {
		return actMet;
	}

	public void setAccesoMet(AccesoMetodo actMet) {
		this.actMet = actMet;
	}

	@Override
	public Tipo chequear(Clase clase, Metodo metodo) throws ErrorSemantico {
		
		Clase claseEstatica = TablaDeSimbolos.getTablaDeSimbolos().getClases().get(t.getLexema());
		
		if(claseEstatica!=null){
			
					Metodo met = claseEstatica.getMetodos().get(this.actMet.getNombre());
					
					if(met!=null){
						
									if(met.isStatic()){
										
										this.actMet.chequearArgumentos(met, clase, metodo);
										
											if(encadenado!=null){
												
												
												
												Clase claseRetorno = TablaDeSimbolos.getTablaDeSimbolos().getClases().get(met.getTipoRetorno().getNombre());
											
												if(claseRetorno!= null)
													return this.encadenado.chequear(claseRetorno,metodo);
												else
													throw new ErrorSemantico(actMet.getLinea()+" : el metodo \""+actMet.getNombre()+"\""+" "
															+ "deberia retornar una clase que contenga el metodo \""+encadenado.getNombre()+"\""
															+"\n\n[Error:"+actMet.getNombre()+"|"+
															actMet.getLinea()+"]");
											}else
												 return met.getTipoRetorno();
										
									}	
									else	
										throw new ErrorSemantico(t.getNroLinea()+" : el metodo con el nombre \""+this.actMet.getNombre()+"\" "
												+ "de la clase \""+claseEstatica.getNombre()+"\" debe ser de tipo estatico"
												+"\n\n[Error:"+this.actMet.getNombre()+"|"+
												t.getNroLinea()+"]");
										
						
					}else
						throw new ErrorSemantico(t.getNroLinea()+" : no existe un metodo con el nombre "
								+ "\""+this.actMet.getNombre()+"\" en la clase \""+claseEstatica.getNombre()+"\""
								+"\n\n[Error:"+t.getLexema()+"|"+
								t.getNroLinea()+"]");
						
			
		}else
			throw new ErrorSemantico(t.getNroLinea()+" : no existe una clase con el nombre \""+t.getLexema()+"\""
					+"\n\n[Error:"+t.getLexema()+"|"+
					t.getNroLinea()+"]");
		
		
	}
	
	
	

}
