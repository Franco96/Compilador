package etapa4;

import java.util.List;

import Excepciones.ErrorSemantico;
import etapa3.Clase;
import etapa3.Metodo;
import etapa3.Variable;
import etapa5.Generador;




public class SentenciaDeclaracionVar extends Sentencia{
	
	
	
	private List<Variable> variablesDeclaradas;
	
	
	

	public SentenciaDeclaracionVar(List<Variable> variablesDeclaradas) {
		
		this.variablesDeclaradas = variablesDeclaradas;
		
	}
	
	
	
	

	@Override
	public void controlSentencia(Clase clase, Metodo metodo)
			throws ErrorSemantico {
		
		
	}

	@Override
	public void generarCodigo() {
		
	
		 int varLocalesGeneradas = Generador.getGenerador().cantVarsLocalesDisponiblesEnUnidad();
		 
		// POR CADA VARIABLE QUE APARESCA EN LA DECLARACION, ASIGNO OFFSET A PARTIR DE LAS VARIABLES YA GENERADAS EN EL METODO.
		for (int i = 0; i < variablesDeclaradas.size(); i++) {
			
			Variable var = variablesDeclaradas.get(i);
			int offset = -(varLocalesGeneradas + i);
			var.setOffset(offset);
		}
		
		Generador.getGenerador().sumarVarsLocalesDisponibles(variablesDeclaradas.size());
		
		// REALIZO LA RESERVA DE ESPACIO PARA LAS VARIABLES DECLARADAS
		Generador.getGenerador().gen("RMEM " + variablesDeclaradas.size(),"# Reservo espacio para las variables declaradas");
		
		
	}
	
	
	

}
