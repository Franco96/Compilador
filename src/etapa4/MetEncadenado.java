package etapa4;


import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.TablaDeSimbolos;
import etapa3.Tipo;

public class MetEncadenado extends Encadenado{

	private AccesoMetodo actMet;
	
	public MetEncadenado(Token token,AccesoMetodo actMet) {
		super(token);
		this.actMet = actMet;
	}

	
	public void setAccesoMetodo(AccesoMetodo actMet){
		this.actMet = actMet;
	}


	@Override
	public Tipo chequear(Clase clase,Metodo metodo) throws ErrorSemantico {
		
		int linea = this.actMet.t.getNroLinea();
		String nombreMetodo = this.actMet.t.getLexema();
		Metodo metodoConvocado = clase.getMetodos().get(nombreMetodo);
		
		
		
		if(metodoConvocado==null){
			
			throw new ErrorSemantico(linea+" : no existe un metodo con el nombre \""+nombreMetodo+"\""+" definido en la clase \""+clase.getNombre()+"\""
					+"\n\n[Error:"+nombreMetodo+"|"+
					linea+"]");
		}
		
		actMet.chequearArgumentos(metodoConvocado, clase, metodo);
		
		if(this.siguiente!=null){
			
			Clase claseRetorno = TablaDeSimbolos.getTablaDeSimbolos().getClases().get(metodoConvocado.getTipoRetorno().getNombre());
			
			if(claseRetorno!=null)
					return this.siguiente.chequear(claseRetorno,metodo);
			else
				throw new ErrorSemantico(linea+" : el metodo \""+nombreMetodo+"\""+" deberia retornar una clase que contenga el metodo \""+siguiente.getNombre()+"\""
						+"\n\n[Error:"+nombreMetodo+"|"+
						linea+"]");
			
			
		}else
			 return metodoConvocado.getTipoRetorno();
	}
	
	
	
}
