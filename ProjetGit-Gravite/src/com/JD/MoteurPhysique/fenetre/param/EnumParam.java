package com.JD.MoteurPhysique.fenetre.param;

import java.util.Hashtable;

public enum EnumParam {
		windowsSize,
		nbObject,
		delay,
		tailleTrace,
		precision,
		poidLune,
		poidTerre,
		configuration;

	private static Hashtable<EnumParam,EnumSuportedParamType> mapParamType = new Hashtable<EnumParam,EnumSuportedParamType>();
	private static Hashtable<EnumParam,String> mapParamInfo = new Hashtable<EnumParam,String>();
	private static Hashtable<EnumParam,ParamVariable<?>> mapParamDefautValeurs = new Hashtable<EnumParam,ParamVariable<?>>();
	private static Hashtable<EnumParam,Double[]> mapParamIntervale = new Hashtable<EnumParam,Double[]>();
	
	
	public static EnumSuportedParamType getType(EnumParam setting) {
		return(mapParamType.get(setting));
	}
	public static String getInfo(EnumParam setting) {
		return(mapParamInfo.get(setting));
	}
	public static ParamVariable<?> getDefautValeurs(EnumParam setting) {
		return(mapParamDefautValeurs.get(setting));
	}
	public static Double[] getParamIntervale(EnumParam setting) {
		return(mapParamIntervale.get(setting));
	}
	
	
	public static void setUpTypes() {
		EnumParam[] nom = {
				EnumParam.windowsSize,
				EnumParam.nbObject,
				EnumParam.delay,
				EnumParam.tailleTrace,
				EnumParam.precision,
				EnumParam.poidLune,
				EnumParam.poidTerre,
				EnumParam.configuration
		};
	
		EnumSuportedParamType[] type = {
				EnumSuportedParamType.nombre,
				EnumSuportedParamType.nombre,
				EnumSuportedParamType.nombre,
				EnumSuportedParamType.nombre,
				EnumSuportedParamType.nombre,
				EnumSuportedParamType.virgule,
				EnumSuportedParamType.virgule,
				EnumSuportedParamType.texte
		};
		String[] info = {
				"Taille de la fenetre",
				"Nombre d'objet",
				"delaie entre les tours",
				"Taille de la trace des objets",
				"précision des orbites",
				"masse des planetes",
				"masse des/du soleil",
				"type de configuration (1-3)"
		};
		ParamVariable<?>[] defautValeur = {
				new ParamVariable<Integer>(800),
				new ParamVariable<Integer>(10),
				new ParamVariable<Integer>(1),
				new ParamVariable<Integer>(1000000),
				new ParamVariable<Integer>(1000),
				new ParamVariable<Double>(0.0000002D),
				new ParamVariable<Double>(80000D),
				new ParamVariable<String>("1")
		};
		Double[][] intervaleValeur = {
				{100D,2_000D},
				{1D,2_000_000D},
				{0D,500_000D},
				{0D,2_000_000D},
				{1D,2_000_000D},
				{0.00000000000000001D,2_000_000D},
				{0.00000000000000001D,2_000_000D},
				{1D,1D}
		};
		
		for(int i = 0 ; i < nom.length ; i++) {
			mapParamType.put(nom[i], type[i]);
			mapParamInfo.put(nom[i], info[i]);
			mapParamDefautValeurs.put(nom[i], defautValeur[i]);
			mapParamIntervale.put(nom[i], intervaleValeur[i]);
		}
	}
}