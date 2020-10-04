package etapa1;

import java.io.FileReader;
import java.io.IOException;

public class GestorDeArchivo {

	private FileReader archivo;	
	private int nroLinea;
	

	
	public GestorDeArchivo(String ruta_archivo) throws IOException{
		
	
			archivo = new FileReader(ruta_archivo);
			nroLinea = 1;
			
			
			
	}
	
	
	public int proximoCaracter() throws IOException{
	   
	  
	  int caracter = archivo.read();	
		
	  if(caracter == 10) //NUEVA LINEA
	      nroLinea++;
	         
	   
	  
	  return caracter;
		
		
	}

	
	
	public int getNroLinea() {
		return nroLinea;
	}
	

	


	public boolean esEOF(int caracter){
		
		return caracter == -1;
	}
	
	
	
	
	
	
}
