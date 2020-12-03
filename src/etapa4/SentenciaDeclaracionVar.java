package etapa4;

import Excepciones.ErrorSemantico;
import etapa3.Clase;
import etapa3.Metodo;


/*ESTA CLASE SE UTILIZA SOLO PARA EL LOGRO DE DETECCION DE CODIGO MUERTO
 * YA QUE NESESITAMOS TENER UN CONTROL DEL ORDEN DE LA DECLARACION DE LAS VARIABLES
 * SI SE DECLARA LUEGO DE UN RETURN SE REPORTARA EL ERROR DE CODIGO MUERTO*/

public class SentenciaDeclaracionVar extends Sentencia{

	@Override
	public void controlSentencia(Clase clase, Metodo metodo)
			throws ErrorSemantico {
		
		
	}

}
