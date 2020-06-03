package com.JD.MoteurPhysique.manager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ReadOnlyFileManager {
	private BufferedReader fichier;
 
    
    public ReadOnlyFileManager(String chemainFichier ) {
    	System.out.println("[ReadOnlyFileManager:14] "+RessourceLoader.loadFile(chemainFichier));
    	this.fichier = RessourceLoader.loadFile(chemainFichier);
    	
    }
    
    public List<String> lectureLigneParLigne(){
    	LinkedList<String> donneesFichier = new LinkedList<String>();
		try {
  
    	String ligne = this.fichier.readLine();
    	
    	while(ligne != null) {
    		donneesFichier.add(ligne);
    		ligne = this.fichier.readLine();
    	}
    	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return(donneesFichier);
    }
}