package etapa4;

import java.util.LinkedList;
import java.util.List;
import Excepciones.ErrorSemantico;
import etapa1.Token;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.TablaDeSimbolos;
import etapa3.Tipo;

public class AccesoMetodo extends Acceso{
	
	private List<Expresion> argumentos;

	public AccesoMetodo(Token t) {
		super(t);
		this.argumentos = new LinkedList<Expresion>();
	}
	
	
	public void addArgumentos(Expresion sent) {
		this.argumentos.add(sent);		
	}


	@Override
	public Tipo chequear(Clase clase,Metodo metodo) throws ErrorSemantico {
		
		Metodo metodoConvocado = clase.getMetodos().get(t.getLexema());
		
		if(metodoConvocado==null){
			throw new ErrorSemantico(t.getNroLinea()+" : no existe un metodo con el nombre \""+t.getLexema()+"\""+" "
					+ "definido en la clase \""+clase.getNombre()+"\""
					+"\n\n[Error:"+t.getLexema()+"|"+
					t.getNroLinea()+"]");
		}
		
		if(metodo.isStatic()&& metodoConvocado.isDynamic())
			throw new ErrorSemantico(t.getNroLinea()+" : no se puede llamar a un metodo dinamico dentro de un "
					+ "metodo estatico"
					+"\n\n[Error:"+metodoConvocado.getNombre()+"|"+
					t.getNroLinea()+"]");
		
		
		chequearArgumentos(metodoConvocado,clase,metodo);
		
		if(this.encadenado!=null){
		
			Clase claseRetorno = TablaDeSimbolos.getTablaDeSimbolos().getClases().get(metodoConvocado.getTipoRetorno().getNombre());
			
			if(claseRetorno!=null)
					return this.encadenado.chequear(claseRetorno,metodo);//Le paso la clase que retorna el metodo y el metodo donde se convoca el encadenado
			else
					throw new ErrorSemantico(t.getNroLinea()+" : el metodo \""+t.getLexema()+"\""+" "
							+ "deberia retornar una clase que contenga el metodo \""+encadenado.getNombre()+"\""
							+"\n\n[Error:"+t.getLexema()+"|"+
							t.getNroLinea()+"]");
			
		
		}else
			 return metodoConvocado.getTipoRetorno();
	
	}		

	
	
	
	protected void chequearArgumentos(Metodo metodoConvocado,Clase clase,Metodo metodo) throws ErrorSemantico{
		
		if(metodoConvocado.getParametros().size() != this.argumentos.size())
			throw new ErrorSemantico(t.getNroLinea()+" : no coincide el numero de parametros actuales con el metodo/constructor \""+t.getLexema()+"\""
					+"\n\n[Error:"+t.getLexema()+"|"+
					t.getNroLinea()+"]");
		
		
		for(int i = 0 ; i<metodoConvocado.getParametros().size() ; i++){
		
			Tipo tipoArgFormal = metodoConvocado.getParametro(i).getTipo();
			
			Tipo tipoArgActual = this.argumentos.get(i).chequear(clase, metodo);
			
			if(!tipoArgActual.conforma(tipoArgFormal))
				
				throw new ErrorSemantico(t.getNroLinea()+" : el parametro actual numero \""+(i+1)+"\" del metodo/constructor "
						+ "\""+t.getLexema()+"\" no conforma con su parametro formal"
						+"\n\n[Error:"+t.getLexema()+"|"+
						t.getNroLinea()+"]");
			
		}
			
		
	}
	
}
