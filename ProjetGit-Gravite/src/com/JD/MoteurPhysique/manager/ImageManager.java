package com.JD.MoteurPhysique.manager;

import java.awt.image.BufferedImage;
import java.util.Hashtable;

public class ImageManager {
	private Hashtable<EnumImages,String> imageLocation = new Hashtable<EnumImages,String>();
	private BufferedImage errorImage;
	
	public ImageManager() {
		EnumImages[] name = {
				EnumImages.background_param
		};
		String[] location = {
				"images/background_param.png"
		};
		
		for(int i = 0 ; i < name.length ; i++)
			this.imageLocation.put(name[i], location[i]);
		
		this.errorImage = RessourceLoader.loadImage("images/image_110erreur.png");
		if(null == this.errorImage)
			System.out.println("[GestionImage:33] erreur recupération de l'image d'erreur");
	}

	public BufferedImage getImage(EnumImages image) {
		String lienImage = this.imageLocation.get(image);
		return(this.getImage(lienImage));
	}
	public BufferedImage getImage(String lienImage) {
		BufferedImage imageRetour = RessourceLoader.loadImage(lienImage);
		if(null == imageRetour) {
			System.out.println("[GestionImage:49] impossible d'obtenir l'image a l'emplacement suivant : "+lienImage);
			return(this.errorImage);
		}
		return(imageRetour);
	}
}