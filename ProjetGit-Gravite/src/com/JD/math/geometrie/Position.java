package com.JD.math.geometrie;

import java.awt.Graphics;

import com.JD.math.general.Constante;

public class Position extends MathGraphique implements Comparable<Position>{
	private Double x;
	private Double y;
	

	// constructeur
	public Position(Double xe , Double ye) {
		this.x = xe;
		this.y = ye;
	}
	public Position(int xe , int ye) {
		this.x = xe+0D;
		this.y = ye+0D;
	}
	
	
	
	// getters
	public Double getX(){
		return(this.x);
	}
	public Double getY(){
		return(this.y);
	}
	public void setX(Double x) {
		this.x = x+0F;
	}
	public void setY(Double y) {
		this.y = y+0F;
	}
	
	
	
	
	
	// calcule la distance entre deux points
	public static Double getDistance(Position p1 , Position p2){
		Double distance = 0.0D;
		if(p1 != null && p2 != null) {
			Double x = p1.getX()-p2.getX();
			Double y = p1.getY()-p2.getY();
			double temp = Math.abs((x*x)+(y*y));
			if(temp != 0)
				distance = Math.sqrt(temp);
		}
		return(distance);
	}
	// trouve la position du centre des deux points passé en paramétre
	public static Position centralPosition(Position p1, Position p2) {
		return(new Position(p1.getX()-(p1.getX()-p2.getX())/2, p1.getY()-(p1.getY()-p2.getY())/2));
	}
	
	// translate le point par rapport au vecteur
	public void addVector(Vecteur vector) {
		this.x = this.getX()+vector.getLongueurX();
		this.y = this.getY()+vector.getLongueurY();
	}
	
	// permet de renvoyer le point le plus loins dans le sens horaire ou antihoraire par rapport a positionAutre
	public static Position getSens(Position solution1 , Position solution2 , Position positionAutre ,boolean horraire) {
		double rayon = Position.getDistance(positionAutre, solution1);
		Position pointRef = new Position(positionAutre.getX()-rayon, positionAutre.getY());
		double distanceS1 = pointRef.getX()+solution1.getX();
		double distanceS2 = pointRef.getX()+solution2.getX();
		
		Position resultat = solution2;
		boolean S1;
		boolean partieHauteS1 = solution1.getY() > positionAutre.getY();
		boolean partieHauteS2 = solution2.getY() > positionAutre.getY();
		boolean partieGaucheS1 = solution1.getX() < positionAutre.getX();
		boolean memeHemisphere = partieHauteS1 && partieHauteS2 || !partieHauteS1 && !partieHauteS2;
		
		if(memeHemisphere) {
			// meme Hemisphere
			if(partieHauteS1) {
				S1 = distanceS1 > distanceS2;
			}else {
				S1 = distanceS1 < distanceS2;
			}
		}else {
			// differents Hemisphere
			if(partieGaucheS1) {
				S1 = solution1.getY() > solution2.getY();
			}else {
				S1 = solution1.getY() < solution2.getY();
			}
		}
		
		if(!horraire)
			S1 = !S1;
		if(S1)
			resultat = solution1;
		return(resultat);
	}
	
	
	
	


	
	
	

	@Override
	public void drawMathObject(int limiteX, int limiteY, Graphics g , double zoom) {
		Double x = this.getX()*zoom;
		Double y = this.getY()*zoom;
		if(x < limiteX && y < limiteY) {
			g.setColor(this.getColor());
			g.drawLine(x.intValue()-2, y.intValue()-2, x.intValue()+2, y.intValue()+2);
			g.drawLine(x.intValue()+2, y.intValue()-2, x.intValue()-2, y.intValue()+2);
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;

		boolean sameX = Math.abs(this.x-other.getX()) < Constante.DELTACOMPARAISONDOUBLE;
			sameX = this.x.equals(other.x);
		boolean sameY = Math.abs(this.y-other.getY()) < Constante.DELTACOMPARAISONDOUBLE;
			sameY = this.y.equals(other.y);
			
		return(sameX && sameY);
	}
	@Override
	public String toString() {
		String retour = "";
		
		retour += "["+this.getX()+" , "+this.getY()+"]";
		
		return(retour);
	}
	@Override
	public int compareTo(Position o) {
		Double d1 = Position.getDistance(this, o);
		int resultat = (int)(d1*14000000);
		return(resultat);
	}
}