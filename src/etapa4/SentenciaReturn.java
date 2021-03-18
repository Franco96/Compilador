package etapa4;

import Excepciones.ErrorSemantico;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.Tipo;
import etapa3.TipoVoid;
import etapa5.Generador;

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



	@Override
	public void generarCodigo() {
		
		
		
		Metodo metodoActual = Generador.getGenerador().getMetodoActualAGenerar();
		
		
		
		if(this.exp!=null){
			
			// Genero la expresión de retorno, cuyo valor resultante acabará en el tope de la pila.
			exp.generarCodigo();;
			
		   Generador.getGenerador().gen("STORE " + metodoActual.getOffsetDeRetorno(), "# Almaceno en la componente de retorno el valor del tope de la pila");
		// Obtengo la cantidad de variables locales para las cuales el espacio fue reservado en el RA hasta ESTE momento.
			
		}
		
		int espacioVar = Generador.getGenerador().cantVarsLocalesDisponiblesEnUnidad();
		
		Generador.getGenerador().gen("FMEM " + espacioVar, "# Elimino espacio empleado por variables locales hasta este punto");	
		
		
		// Procedo a recuperar la referencia al RA llamador del RA asociado a la rutina que finaliza
		Generador.getGenerador().gen("STOREFP","# Recupero la dirección base del RA llamador (uso el ED)");
		
		
		if (metodoActual.isStatic()) 
			
			Generador.getGenerador().gen("RET " + metodoActual.getParametros().size(),
										"# Retorno: libero " + metodoActual.getParametros().size() + " espacios");		
		else 
			Generador.getGenerador().gen("RET " + (metodoActual.getParametros().size() + 1),
										"# Retorno: libero " + (metodoActual.getParametros().size() + 1) + " espacios");
							
		
	}
	
	
	
	
}
