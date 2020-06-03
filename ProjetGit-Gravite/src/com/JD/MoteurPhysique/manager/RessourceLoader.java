package com.JD.MoteurPhysique.manager;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

public final class RessourceLoader {
	
	private RessourceLoader() {}
	
	// permet de lire des images aprés la compilation
	public static BufferedImage loadImage(String path) {
		InputStream input = RessourceLoader.class.getResourceAsStream(path);
		if( null == input) {
			input = RessourceLoader.class.getResourceAsStream("/"+path);
			path = "/"+path;
		}
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(input);
		} catch (IOException e) {
			System.out.println("[RessourceLoader:29] erreur dans la recupération de l'image [\""+path+"\"]");
			e.printStackTrace();
		}
		
		System.out.println("IMMAGE : "+path);
		return(image);
	}
	
	// permet de lire des fichiers aprés la compilation
	public static BufferedReader loadFile(String path) {
		InputStream in = RessourceLoader.class.getResourceAsStream(path); 
		if(null == in) {
			in = RessourceLoader.class.getResourceAsStream("/"+path); 
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		return(reader);
	}
}