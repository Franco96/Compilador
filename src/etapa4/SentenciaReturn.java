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
	

	@Override
	public void controlSentencia(Clase clase, Metodo metodo)
			throws ErrorSemantico {
		
		if(exp==null){ //return;
		
				if(!(metodo.getTipoRetorno() instanceof TipoVoid))
					throw new ErrorSemantico(metodo.getLinea()+" : el metodo \""+metodo.getNombre()+"\" debe retornar un tipo de valor"
							+"\n\n[Error:"+metodo.getNombre()+"|"+
							metodo.getLinea()+"]");
		}else{
				Tipo tipoRetorno = exp.chequear(clase, metodo);
				
				if(!tipoRetorno.conforma(metodo.getTipoRetorno()))
					throw new ErrorSemantico(metodo.getLinea()+" : el metodo \""+metodo.getNombre()+"\" debe retornar un tipo "
							+ "\""+metodo.getTipoRetorno().getNombre()+"\""
							+"\n\n[Error:"+metodo.getNombre()+"|"+
							metodo.getLinea()+"]");
			
		}
	}
	
	
	
	
}
