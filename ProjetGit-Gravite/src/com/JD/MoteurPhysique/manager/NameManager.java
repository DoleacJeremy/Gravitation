package com.JD.MoteurPhysique.manager;

import java.util.ArrayList;
import java.util.List;

public class NameManager {
	private static ArrayList<String> noms;
	
	// récupére la liste des noms depuis le fichier
	private static void setUpList() {
		if(noms == null) {
			ReadOnlyFileManager fichier = new ReadOnlyFileManager("dataSaved/name.txt");
			noms = new ArrayList<String>();
			noms.addAll(fichier.lectureLigneParLigne());
		}
	}
	// renvoi la liste des noms
	public static List<String> getNames(){
		setUpList();
		return(noms);
	}
	// renvoi un nom random dans la liste
	public static String getName() {
		setUpList();
		int index = (int)(Math.random()*noms.size());
		return(noms.get(index));
	}	
}