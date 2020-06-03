package com.JD.MoteurPhysique.core;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.JD.MoteurPhysique.fenetre.param.EnumParam;
import com.JD.MoteurPhysique.manager.ParamSettingsManager;
import com.JD.math.geometrie.Position;
import com.JD.math.geometrie.Vecteur;

public abstract class GravityObjet implements Cloneable {
	private Position position;
	private Vecteur direction;
	private int size;
	private Double poid;
	private int tailleFenetre;
	private boolean unMovable = false;
	
	private ArrayList<Position> traceOrbitale = new ArrayList<Position>();
	private int traceTaille =100;
	Color traceColor = new Color(50, 100, 175); //rouge,vert,bleu

	// constructor
	public GravityObjet(int size, Double poid , int tailleFenetre) {
		ParamSettingsManager paramSettingsManager = ParamSettingsManager.getParamSettingsUser();
		this.traceTaille = (Integer)paramSettingsManager.getParam(EnumParam.tailleTrace).getValue();
		
		this.poid = poid;
		this.size = size;
		this.tailleFenetre = tailleFenetre;

		Double x =  (Math.random() * (tailleFenetre/3D - 40) + 20)+tailleFenetre/3D;
		Double y =  (Math.random() * (tailleFenetre/3D - 40) + 20)+tailleFenetre/3D;

		this.position = new Position(x, y);
		this.direction = new Vecteur(new Position(0,0));
	}

	// getters/setters
	public Position getPosition() {
		return (this.position);
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public int getSize() {
		return(this.size);
	}
	public Double getPoid() {
		return(this.poid);
	}
	public Color getColor() {
		return(Color.WHITE);
	}
	public Vecteur getDirection() {
		return(this.direction);
	}
	public void setDirection(Vecteur direction) {
		this.direction = direction;
	}
	public int getTraceSize() {
		return(this.traceTaille);
	}
	public void setTrace(List<Position> trace) {
		this.traceOrbitale = (ArrayList<Position>) trace;
	}
	public Color getTraceColor() {
		return(this.traceColor);
	}
	public List<Position> getTrace(){
		return(this.traceOrbitale);
	}
	public boolean estBougeable() {
		return(this.unMovable);
	}
	public void setUnMovavle(boolean unMovable) {
		this.unMovable = unMovable;
	}

	// vérifie si l'objet touche l'objet en paramétre
	public boolean touche(GravityObjet other) {
		Position positionOther = other.getPosition();
		Position positionCurrent = this.position;

		return (Position.getDistance(positionOther, positionCurrent) < (this.size+other.getSize())/2);
	}
	
	
	// utilise l'algorithme pour obtenir la direction dans laquelle se déplace
	public void chooseDirection(List<GravityObjet> listeStar) {
		if(!this.unMovable) {
			this.direction.translater(ConcreteGravityPhysique.directionMovement(this , listeStar));
		}
	}
	// applique le mouvement calculé avec la methode choosedirection
	public void moveToDirection() {
		if(!this.unMovable) {
			Vecteur newDorecton = new Vecteur(new Position(this.direction.getLongueurX(), this.direction.getLongueurY()));
			this.position.addVector(newDorecton);
			
			// ajout de la position a la trace si elle n'est pas déjà stoqué
			Position tempPosition = new Position(this.position.getX(),this.position.getY());
			if(!this.traceOrbitale.contains(tempPosition))
				this.traceOrbitale.add(tempPosition);
			while(this.traceOrbitale.size() > this.traceTaille) {
				this.traceOrbitale = new ArrayList<Position>();
			}
		}
	}
	
	// permet de savoir si l'objet doit etre supprimé
	public boolean isRemovable() {
		return(this.position.getY() > this.tailleFenetre || this.position.getY() < 0 || this.position.getX() > this.tailleFenetre || this.position.getX() < 0);
	}
	
	
	

	public abstract void drawCell(Graphics g);
    public Object clone() { 
    	Object object = null;
    	try {
    		object = super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("[PhysicObject:127] clone raté");
			e.printStackTrace();
		} 
		return(object);
	} 
	public String cellToString() {
		String save = "";
		
		save += "position:["+this.position.getX()+":"+this.position.getY()+"]";
		save += "direction:["+this.direction.getLongueurX()+":"+this.direction.getLongueurY()+"]";
		save += "size:["+this.size+"]";
		save += "poid:["+this.poid+"]";
		
		return(save);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return(true);
		if (obj == null)
			return(false);
		if (this.getClass() != obj.getClass())
			return(false);
		GravityObjet other = (GravityObjet) obj;
		
		return(this.position.equals(other.position));
	}

}