package com.JD.math.geometrie;

public class Vecteur {
	private Position destination;
	
	// constructeurs
	public Vecteur(Position pointDestination) {
		this.destination = pointDestination;
	}
	public Vecteur(Position pointOrigin , Position pointDestination) {
		Double x = pointDestination.getX()-pointOrigin.getX();
		Double y = pointDestination.getY()-pointOrigin.getY();
		this.destination = new Position(x,y);
	}
	
	
	// getter/setters
	public Double getLongueurX(){
		Double x = this.destination.getX();
		return(x);
	}
	public Double getLongueurY(){
		Double y = this.destination.getY();
		return(y);
	}
	public Position getDestinationPoint(){
		return(this.destination);
	}
	// calcule et retourne la longueur du vecteur
	public Double getLongueur(){
		Double x = Math.abs(this.destination.getX());
		Double y = Math.abs(this.destination.getY());
		return(Math.sqrt(y*y+x*x));
	}
	// redimensionne à la longueur voulue
	public void setLongueur(Double distance) {
		Double rapport = distance/this.getLongueur();
		Double x = this.destination.getX()*rapport;
		Double y = this.destination.getY()*rapport;
		this.destination = new Position(x,y);
	}
	
	
	// translation d'un point
	public Position translater(Position p){
		Position resultat = new Position(p.getX()+this.getLongueurX(),p.getY()+this.getLongueurY());
		return(resultat);
	}
	// translation du vecteur par un autre vecteur
	public void translater(Vecteur v) {
		this.destination = this.translater(v.getDestinationPoint());
	}
	// inverse la direction X du vecteur
	public void invertedX() {
		this.destination.setX(this.destination.getX()*(-1));
	}
	// inverse la direction Y du vecteur
	public void invertedY() {
		this.destination.setY(this.destination.getY()*(-1));
	}
	// inverse la direction du vecteur
	public void inverted() {
		this.invertedX();
		this.invertedY();
	}
	// retir la partie X du vecteur
	public void removeX() {
		Double distance = this.getLongueur();
		this.destination.setX(0D);
		this.setLongueur(distance);
	}
	// retir la partie Y du vecteur
	public void removeY() {
		Double distance = this.getLongueur();
		this.destination.setY(0D);
		this.setLongueur(distance);
	}
	
	
	
	
	@Override
	public String toString() {
		return "Vecteur ["+destination+"]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
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
		Vecteur other = (Vecteur) obj;
		if (this.destination == null) {
			if (other.destination != null)
				return false;
		} else if (!this.destination.equals(other.destination))
			return false;
		return true;
	}
}