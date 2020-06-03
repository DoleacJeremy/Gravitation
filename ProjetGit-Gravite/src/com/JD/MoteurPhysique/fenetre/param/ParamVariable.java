package com.JD.MoteurPhysique.fenetre.param;

public class ParamVariable<T> {
	private T paramValue;
	
	public ParamVariable(T paramValue) {
		this.paramValue = paramValue;
	}
	
	public T getValue() {
		return(this.paramValue);
	}
	
	// vérifie que le paramétre est valide
	public static boolean isParamValid(EnumParam param, String textValue) {
		boolean dataError = false;
		EnumSuportedParamType paramType = EnumParam.getType(param);
		Double[] intervaleValide = EnumParam.getParamIntervale(param);
		
		
		switch(paramType) {
			case nombre :
				try {
					int paramUserValue = Integer.parseInt(textValue);
					if(paramUserValue < intervaleValide[0] || paramUserValue > intervaleValide[1]) 
						dataError = true;
				}catch(Exception e) {
					dataError = true;
				}
				break;
			case texte :
				if(textValue.length() < 1) {
					dataError = true; 
				}
				break;
			case virgule :
				try {
					double paramUserValue = Double.parseDouble(textValue);
					if(paramUserValue < intervaleValide[0] || paramUserValue > intervaleValide[1]) 
						dataError = true;
				}catch(Exception e){
					dataError = true; 
				}
		}
		return(!dataError);
	}
}