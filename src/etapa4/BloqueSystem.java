package etapa4;


import etapa5.Generador;




public class BloqueSystem extends Bloque{
	
	private String nombreMetodo;
	
	public BloqueSystem(String nombreMetodo){
		
		this.nombreMetodo=nombreMetodo;
	}

	@Override
	public void generarCodigo() {
		
		// Dependiendo el nombre de la unidad a la cual se asocia este bloque system, genero el código correcto.
		
		switch (nombreMetodo) {
			
			case "printS":	
				// Cargo el valor correspondiente al parámetro a imprimir en el tope de la pila, e imprimo (String).
				Generador.getGenerador().gen("LOAD 3", "# Cargo el valor del parametro");
				Generador.getGenerador().gen("SPRINT", "# Escribo el String por pantalla");				
				break;
			
			case "printSln": {
				// Cargo el valor correspondiente al parámetro a imprimir en el tope de la pila y imprimo seguido de un ENTER (String).
				Generador.getGenerador().gen("LOAD 3", "# Cargo el valor del parametro");
				Generador.getGenerador().gen("SPRINT", "# Escribo el String por pantalla");
				Generador.getGenerador().gen("PRNLN", "# Imprimo el salto de linea");
				break;
			}
			case "printI": {
				// Cargo el valor correspondiente al parámetro a imprimir en el tope de la pila, e imprimo (Entero).
				Generador.getGenerador().gen("LOAD 3","# Cargo el valor entero del parametro");
				Generador.getGenerador().gen("IPRINT","# Imprimo el valor entero");				
				break;
			}
			case "printIln": {
				// Cargo el valor correspondiente al parámetro a imprimir en el tope de la pila y imprimo seguido de un ENTER (Entero).
				Generador.getGenerador().gen("LOAD 3", "# Cargo el valor entero del parametro");
				Generador.getGenerador().gen("IPRINT", "# Imprimo el valor entero");
				Generador.getGenerador().gen("PRNLN", "# Imprimo el salto de linea");
				break;
			}			
			case "printB": {
				// Cargo el valor correspondiente al parámetro a imprimir en el tope de la pila, e imprimo (Boolean).
				Generador.getGenerador().gen("LOAD 3", "# Cargo el valor del parametro");
				Generador.getGenerador().gen("BPRINT", "# Imprimo el valor booleano");
				break;
			}
			case "printBln": {
				// Cargo el valor correspondiente al parámetro a imprimir en el tope de la pila y imprimo seguido de un ENTER (Boolean).
				Generador.getGenerador().gen("LOAD 3","# Cargo el valor del parametro");
				Generador.getGenerador().gen("BPRINT","# Imprimo el valor booleano");
				Generador.getGenerador().gen("PRNLN","# Imprimo el salto de linea");
				break;
			}
			case "printC": {
				// Cargo el valor correspondiente al parámetro a imprimir en el tope de la pila, e imprimo (Char).
				Generador.getGenerador().gen("LOAD 3","# Cargo el valor del parametro");
				Generador.getGenerador().gen("CPRINT","# Imprimo el caracter");
				break;
			}
			case "printCln": {
				// Cargo el valor correspondiente al parámetro a imprimir en el tope de la pila y imprimo seguido de un ENTER (Char).
				Generador.getGenerador().gen("LOAD 3","# Cargo el valor del parametro");
				Generador.getGenerador().gen("CPRINT","# Imprimo el caracter");
				Generador.getGenerador().gen("PRNLN","# Imprimo el salto de linea");
				break;
			}
			case "println": {
				// Imprimo un salto de línea.
				Generador.getGenerador().gen("PRNLN","# Imprimo el salto de linea");
				break;
			}
			case "read": {
				// Obtengo el dato del buffer y lo almaceno en el espacio de retorno.
				Generador.getGenerador().gen("READ","# Consigo el valor ingresado por pantalla");
				Generador.getGenerador().gen("STORE 3","# Lo guardo en la seccion de retorno");
				break;
			}			
		}
		
		
	}
	
	
	
	
	
	
}	
	
