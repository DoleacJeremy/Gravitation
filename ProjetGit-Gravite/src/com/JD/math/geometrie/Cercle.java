package com.JD.math.geometrie;

import java.awt.Graphics;

import com.JD.math.general.Constante;

public class Cercle extends MathGraphique{
	private Position centre;
	private Position positionRayon;
	
	// constructeur
	public Cercle(Position centre , Position rayon) {
		this.centre = new Position(centre.getX(), centre.getY());
		this.positionRayon = new Position(rayon.getX(), rayon.getY());
	}
	public Cercle(Position centre , double rayon) {
		this.centre = new Position(centre.getX(), centre.getY());
		this.positionRayon = new Position(centre.getX()-rayon, centre.getY());
	}
	
	//getters
	public double getRayon() {
		return(Position.getDistance(this.centre, this.positionRayon));
	}
	public Position getCentre() {
		return(this.centre);
	}
	
	
	
	// permet de savoir si le point passé en paramétre appartient au cercle
	public boolean appartienPerimetreCercle(Position point) {
		double cercleX = this.centre.getX();
		double cercleY = this.centre.getY();
		double cercleRayon = this.getRayon();

		double partiI = (point.getX()-cercleX)*(point.getX()-cercleX);
		double partiII = (point.getY()-cercleY)*(point.getY()-cercleY);
		double rcarre = cercleRayon*cercleRayon;
		
		boolean appartenanceCercle = (partiI+partiII+Constante.DELTACOMPARAISONDOUBLE) >= rcarre && 
									 (partiI+partiII-Constante.DELTACOMPARAISONDOUBLE) <= rcarre;
		return(appartenanceCercle);
	}
	
	@Override
	public void drawMathObject(int limiteX, int limiteY, Graphics g , double zoom) {
		Double x = (this.centre.getX()-this.getRayon())*zoom;
		Double y = (this.centre.getY()-this.getRayon())*zoom;
		int taille = (int)(this.getRayon()*2*zoom);
		g.drawOval(x.intValue() , y.intValue() , taille , taille);
	}
}