package etapa4;

import Excepciones.ErrorSemantico;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.Tipo;
import etapa3.TipoVoid;

public class SentenciaReturn extends Sentencia{

	private Expresion exp;


	public SentenciaReturn(Expresion exp) {
	
		this.exp = exp;
	}
	
	
	
	public Expresion getExpresion(){
		return exp;
	}
	

	@Override
	public void controlSentencia(Clase clase, Metodo metodo) throws ErrorSemantico {
		
		if(exp==null){ //return;
			
			if(!clase.getNombre().equals(metodo.getNombre())) // Si no es el constructor
				if(!(metodo.getTipoRetorno() instanceof TipoVoid))
					throw new ErrorSemantico(metodo.getLinea()+" : el metodo \""+metodo.getNombre()+"\" debe retornar un tipo de valor"
							+"\n\n[Error:"+metodo.getNombre()+"|"+
							metodo.getLinea()+"]");
		}else{
				Tipo tipoRetorno = exp.chequear(clase, metodo);
				
				if( metodo.getTipoRetorno() instanceof TipoVoid)
					throw new ErrorSemantico(metodo.getLinea()+" : el metodo \""+metodo.getNombre()+"\" no debe retornar ningun valor porque es de tipo "
							+ "\""+metodo.getTipoRetorno().getNombre()+"\""
							+"\n\n[Error:"+metodo.getNombre()+"|"+
							metodo.getLinea()+"]");
				
				if(clase.getNombre().equals(metodo.getNombre())){
					
					throw new ErrorSemantico(metodo.getLinea()+" : el constructor de la clase \""+metodo.getNombre()+"\" no debe retornar ningun valor "
							+"\n\n[Error:"+metodo.getNombre()+"|"+
							metodo.getLinea()+"]");
					
				}
					
				
				if(!tipoRetorno.conforma(metodo.getTipoRetorno()))
					throw new ErrorSemantico(metodo.getLinea()+" : el metodo \""+metodo.getNombre()+"\" debe retornar un tipo "
							+ "\""+metodo.getTipoRetorno().getNombre()+"\""
							+"\n\n[Error:"+metodo.getNombre()+"|"+
							metodo.getLinea()+"]");
			
		}
		
		
	}
	
	
	
	
}
