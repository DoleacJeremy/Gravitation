package com.JD.MoteurPhysique.core;

import java.util.ArrayList;
import java.util.List;

import com.JD.MoteurPhysique.fenetre.param.EnumParam;
import com.JD.MoteurPhysique.manager.ParamSettingsManager;
import com.JD.math.geometrie.Cercle;
import com.JD.math.geometrie.Droite;
import com.JD.math.geometrie.FunctionGeometrie;
import com.JD.math.geometrie.Position;
import com.JD.math.geometrie.Vecteur;

public class ConcreteGravityPhysique {
	public static double GRAVITATIONALACCELERATION = 1D;

	private ConcreteGravityPhysique() {}
	
	//  calcule la force d'un objet par rapport a la liste de tous les objets
	public static Vecteur directionMovement(GravityObjet orbiterObject , List<GravityObjet> otherOjects) {		
		Vecteur finalDirection = new Vecteur(new Position(0,0));
		if(otherOjects.size() > 1) {
			for(GravityObjet otherCell : otherOjects) {
				if(!otherCell.equals(orbiterObject)) {
					finalDirection.translater(ConcreteGravityPhysique.calculateGravitationalVector(orbiterObject.getPosition(), orbiterObject.getPoid(), otherCell.getPosition(), otherCell.getPoid()));
				}
			}
		}
		return(finalDirection);
	}
	
	// calcule la force d'un objet par rapport a un autre
	public static Vecteur calculateGravitationalVector(Position positionBase , double masseBase , Position positionAutre , double masseAutre) {
		ParamSettingsManager paramSettingsManager = ParamSettingsManager.getParamSettingsUser();
		Double distance = Position.getDistance(positionBase , positionAutre);
		int precision = 1;
		if(paramSettingsManager.getParam(EnumParam.precision) != null)
			precision = (Integer)paramSettingsManager.getParam(EnumParam.precision).getValue();
		
		// F = G x ((m1 x m2)/d²)
		// with 
		//    G = constante
		//    m1 = mass du premier objet
		//    m2 = mass du second  objet
		//    d = distance
		Vecteur gravite = new Vecteur(positionBase,positionAutre);
		Double tailleVecteur = (ConcreteGravityPhysique.GRAVITATIONALACCELERATION*((masseBase*masseAutre)/(distance*distance)))/masseBase;
		//ralenti la simulation mais permet de crée des orbites plus précise 
		gravite.setLongueur(tailleVecteur/precision);

		return(gravite);
	}

	//calcule une force permettant a l'objet base d'orbiter l'objet autre
	public static Vecteur calculateOrbitalTrajectory(boolean horraire , Position positionOrbiteur , Double masseOrbiteur , Position positionCentrale , Double masseCentrale) {
		// vecteur de l'attirance de l'orbiteur sur l'orbité et le point qui correspond à la nouvelle position de l'orbiteur sans impulsion de base
		Vecteur g = ConcreteGravityPhysique.calculateGravitationalVector(positionOrbiteur, masseOrbiteur, positionCentrale, masseCentrale);
		Position pointG = new Position(positionOrbiteur.getX()+g.getLongueurX(), positionOrbiteur.getY()+g.getLongueurY());
		
		// calcule les points d'intersection du cercle et de la perpendiculaire. points qui correspondent au points ou l'orbiteur doit se trouvé pour etre sur une orbite
		Droite droiteDiametreCercle = new Droite(positionOrbiteur, positionCentrale);
		Droite perpendiculaireDiametreCercle = droiteDiametreCercle.perpendiculaire(pointG);		
		Cercle cercle = new Cercle(positionCentrale, positionOrbiteur);
		ArrayList<Position> solutions = (ArrayList<Position>) FunctionGeometrie.getIntersection(perpendiculaireDiametreCercle, cercle);
		
		// calcul des solution et création du vecteur qui correspond au sens indique (horaire ou antihoraire)
		double oppose = Position.getDistance(solutions.get(0), pointG);
		Position solution = Position.getSens(solutions.get(0), solutions.get(1), positionCentrale, horraire);
		Vecteur impulsionBase = new Vecteur(pointG,solution);
		impulsionBase.setLongueur(oppose/1.415);
		
		return(impulsionBase);
	}
	
	//permet de faire s'orbiter 2 objets
	public static Vecteur[] calculateOrbitalTrajectory2ObjectsSystem(boolean horraire, GravityObjet object1 , GravityObjet object2 ) {
		Position centre = Position.centralPosition(
				new Position(object1.getPosition().getX(),object1.getPosition().getY()), 
				new Position(object2.getPosition().getX(),object2.getPosition().getY()));
		
		Vecteur g = ConcreteGravityPhysique.calculateGravitationalVector(object1.getPosition(), object1.getPoid(), centre, object2.getPoid());
		Position pointG = new Position(object1.getPosition().getX()+g.getLongueurX(), object1.getPosition().getY()+g.getLongueurY());

		// calcule les points d'intersection du cercle et de la perpendiculaire. points qui correspondent au points ou l'orbiteur doit se trouvé pour etre sur une orbite
		Droite droiteDiametreCercle = new Droite(pointG, centre);
		Droite perpendiculaireDiametreCercle = droiteDiametreCercle.perpendiculaire(pointG);
		Cercle cercle = new Cercle(centre, object1.getPosition());

		// calcul des solution et création du vecteur qui correspond au sens indique (horaire ou antihoraire)
		ArrayList<Position> solutions = (ArrayList<Position>) FunctionGeometrie.getIntersection(perpendiculaireDiametreCercle, cercle);
		double oppose = Position.getDistance(solutions.get(0), pointG);
		Position solution = Position.getSens(solutions.get(0), solutions.get(1), centre, horraire);
		Vecteur forceOrbite = new Vecteur(object1.getPosition(),solution);
		forceOrbite.setLongueur(oppose/2.8295);

		// creation des deux vecteurs a partir de celui calculé pour l'un des deux objet
		Vecteur[] vecteurs = new Vecteur[2];
		vecteurs[0] = new Vecteur(new Position(forceOrbite.getLongueurX(), forceOrbite.getLongueurY()));
		forceOrbite.inverted();
		vecteurs[1] = new Vecteur(new Position(forceOrbite.getLongueurX(), forceOrbite.getLongueurY()));
		return(vecteurs);
	}
}