package com.JD.math.general;

import java.util.ArrayList;
import java.util.List;

public class Functions {
	
	private Functions() {}
	
	// float to int with a better value (1.99999F > 2 instead of 1.99999F > 1)
	public static int round(float floatVar){
		int intPart = (int)floatVar;
		int result = intPart;
		//positive
		if(floatVar > 0) {
			if(floatVar-intPart >= 0.5F)
				result++;
		//negative 
		}else {
			if(intPart-floatVar >= 0.5F)
				result--;
		}
		return(result);
	}
	//vérifie si un nombre a virgule est en fait un nombre entier
	public static boolean estInteger(Double valeur) {
		return(valeur.equals((double)valeur.intValue()));
	}
	
	//résout une equation du second degré
	public static List<Double> calculDelta(double a , double b , double c){
		List<Double> solutions = new ArrayList<Double>();

		double delta = b*b-4*a*c;
		
		if(delta > 0) {
			solutions.add((-b-Math.sqrt(delta))/(2*a));
			solutions.add((-b+Math.sqrt(delta))/(2*a));
		}
		if(delta == 0) {
			solutions.add((-b)/(2*a));
		}
		
		return(solutions);
	}
}