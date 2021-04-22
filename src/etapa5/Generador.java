package etapa5;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


import etapa3.Metodo;




public class Generador {
	
	private static Generador instancia = null;
	
	private List<String> instruccionesCeivm;
	
	private Metodo metodoActualAGenerar;
	
	private int varsLocalesDisponiblesEnUnidad;
	
	private int contEtiquetasDeStrings;
	
	private int contEtiquetasDeIf;
	
	private int contEtiquetasDeWhile;
	
	
	
	private Generador(){
		
		instruccionesCeivm = new LinkedList<String>();
		contEtiquetasDeStrings = 0;
		contEtiquetasDeIf = 0;
		contEtiquetasDeWhile = 0;
		
	}
	
	
	
	
	
	
	public static Generador getGenerador () {
		if (instancia == null) {
			instancia = new Generador();
		}
		return instancia;
	}
	
	
	
	
	
	
	
	public Metodo getMetodoActualAGenerar() {
		return metodoActualAGenerar;
	}






	public void setMetodoActualAGenerar(Metodo metodoActualAGenerar) {
		this.metodoActualAGenerar = metodoActualAGenerar;
	}






	public void saltoDeLinea() {
		instruccionesCeivm.add("");
	}
	
	public void comentario(String comentario){
		instruccionesCeivm.add("# "+comentario);
	}
	
	public void code(){
		this.saltoDeLinea();
		instruccionesCeivm.add(".CODE");
	}
	
	public void data(){
		this.saltoDeLinea();
		instruccionesCeivm.add(".DATA");
	}
	
	public void encabezadoMet(String etiquetaMetodo){
		this.saltoDeLinea();
		instruccionesCeivm.add(etiquetaMetodo+":");
	}
	
	public void gen (String instruccion, String comentario) {
		
		instruccionesCeivm.add("                    "+instruccion + getEspaciosEnBlanco(instruccion)+ comentario);
	}
	
	
	private String getEspaciosEnBlanco(String inst){
	
		String blanco="";
		
		int calculate = 40 - (20+inst.length());
		
		if(calculate < 0)
			calculate = 10; //Por si el comentario pisa la instruccion
		
		for(int i = 0; i<calculate;i++)
			blanco +=" ";
		
		return blanco;
	}
	
	
	
	
	public void nuevoConteoVarLocalesGeneradas () {
		varsLocalesDisponiblesEnUnidad = 0;
	}
	

	public void sumarVarsLocalesDisponibles (int cant) {
		varsLocalesDisponiblesEnUnidad += cant;
	}
	
	
	
	public void restarVarsLocalesDisponibles (int cant) {
		varsLocalesDisponiblesEnUnidad -= cant;
	}
	
	
	public int cantVarsLocalesDisponiblesEnUnidad () {
		return varsLocalesDisponiblesEnUnidad;
	}
	
	
	
	
	public int nuevaEtiquetaDeString () {
	
		int nuevaEtiqueta = contEtiquetasDeStrings;
		contEtiquetasDeStrings++;
		return nuevaEtiqueta;
	
	}
	
	public int nuevaEtiquetaDeIf () {
		
		int nuevaEtiqueta = contEtiquetasDeIf;
		contEtiquetasDeIf++;
		return nuevaEtiqueta;
	}
	
	public int nuevaEtiquetaDeWhile () {
		
		int nuevaEtiqueta = contEtiquetasDeWhile;
		contEtiquetasDeWhile++;
		return nuevaEtiqueta;
	}
	
	
	
	public void generarSalida(String nombreArchivoSalida) throws IOException {
		BufferedWriter escritorArchivo;
		int cantSentencias;
		int index;
		escritorArchivo = new BufferedWriter(new FileWriter(nombreArchivoSalida));
		cantSentencias = instruccionesCeivm.size();
		// Si uso un archivo particular (no salida estándar), limpio su contenido.
		escritorArchivo.flush();		
		//Cree el nuevo archivo, escribo todas las sentencias
		for (index = 0; index < cantSentencias; index++) {
			// Escribo al final del archivo una nueva linea de código generado, seguido de un ENTER.
			escritorArchivo.append(instruccionesCeivm.get(index));
			escritorArchivo.newLine();
		}
		
		// Realizo el cierre del archivo de escritura.
		escritorArchivo.close();
		
	}
	
	

}
