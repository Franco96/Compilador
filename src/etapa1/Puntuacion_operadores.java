package etapa1;

import java.util.HashMap;
import java.util.Map;

public class Puntuacion_operadores {
	
	private Map<String,String> puntuacion_operadores;
	
	
	
	public Puntuacion_operadores(){
		
		puntuacion_operadores = new HashMap<String, String>();
		
		puntuacion_operadores.put("(", "T_parenIni");
		puntuacion_operadores.put(")", "T_parenFin");
		puntuacion_operadores.put("{", "T_llavesIni");
		puntuacion_operadores.put("}", "T_llavesFin");
		puntuacion_operadores.put(";", "T_PyC");
		puntuacion_operadores.put(",", "T_coma");
		puntuacion_operadores.put(".", "T_punto");
		puntuacion_operadores.put("=", "op=");
		puntuacion_operadores.put("*", "op*");
		puntuacion_operadores.put("%", "op%");
		puntuacion_operadores.put(">", "op>");
		puntuacion_operadores.put("<", "op<");
		puntuacion_operadores.put("!", "op!");
		puntuacion_operadores.put("+", "op+");
		puntuacion_operadores.put("-", "op-");
		puntuacion_operadores.put(">=", "op>=");
		puntuacion_operadores.put("<=", "op<=");
		puntuacion_operadores.put("!=", "op!=");
		puntuacion_operadores.put("==", "op==");
		puntuacion_operadores.put("+=", "op+=");
		puntuacion_operadores.put("-=", "op-=");
		
		
		
	}
	
	
	public Map<String, String> getPuntuacionOp(){
		
		return puntuacion_operadores;
	}
	

}
