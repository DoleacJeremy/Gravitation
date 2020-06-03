package com.JD.MoteurPhysique.manager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.JD.math.geometrie.Position;

public class StringManager {
    
	private StringManager() {}
	
	//permet de dessiner du texte avec un contour dans une fenetre
	public static void drawStringOutLined(Graphics g , String textToDraw , Position p , Color couleurTexte , Color couleurFond) {
		int tailleFont = 10;
		int distancebord = 5;
		Font font = new Font("TimesRoman", Font.CENTER_BASELINE, tailleFont);
		
		g.setColor(couleurFond);
		g.fillRect((int)(p.getX()+distancebord-1), (int)(p.getY()+distancebord+1),textToDraw.length()*6+1,tailleFont+1);
		g.setFont(font);
		g.setColor(couleurTexte);
		g.drawString(textToDraw, (int)(p.getX()+distancebord), (int)(p.getY()+distancebord)+tailleFont);
	}
}