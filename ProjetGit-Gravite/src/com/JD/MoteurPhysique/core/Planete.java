package com.JD.MoteurPhysique.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.JD.MoteurPhysique.manager.NameManager;

public class Planete extends GravityObjet{
	private String nom;
	
	public Planete(int taille, Double poid , int tailleFenetre) {
		super(taille, poid, tailleFenetre);
		this.nom = NameManager.getName();
		
	}
	public Planete(int taille, Double poid , int tailleFenetre, String nom) {
		super(taille, poid, tailleFenetre);
		this.nom = nom;
	}

	public String getNom() {
		return(this.nom);
	}
	
	public void drawCell(Graphics g) {
		//planete
		g.setColor(this.getColor());
		g.drawOval(this.getPosition().getX().intValue()-this.getSize()/2, this.getPosition().getY().intValue()-this.getSize()/2, this.getSize(), this.getSize());
		
		//trace de la planete
		if(!this.estBougeable()) {
			g.setColor(this.getTraceColor());
			for(int i = 0 ; i < this.getTrace().size()-2 ; i++) {
				g.drawLine(this.getTrace().get(i).getX().intValue(), this.getTrace().get(i).getY().intValue(), 
						   this.getTrace().get(i+1).getX().intValue(), this.getTrace().get(i+1).getY().intValue());
			}
		}

		// nom de la planete
		g.setFont(new Font("Comic Sans MS", Font.PLAIN, 10)); 
		g.setColor(Color.GREEN);
		g.drawString(this.nom, this.getPosition().getX().intValue(), this.getPosition().getY().intValue());
	}

}