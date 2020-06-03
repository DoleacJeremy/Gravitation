package com.JD.MoteurPhysique.core;

import java.util.ArrayList;
import java.util.List;

import com.JD.MoteurPhysique.fenetre.param.EnumParam;
import com.JD.MoteurPhysique.manager.NameManager;
import com.JD.MoteurPhysique.manager.ParamSettingsManager;
import com.JD.math.geometrie.Position;
import com.JD.math.geometrie.Vecteur;

public class PositionementObjet {
	
	private PositionementObjet() {}
	
	// plusieurs lunes sans soleil
	public static List<GravityObjet> nuageOrbite(int nbObjets , int tailleFenetre){
		ArrayList<GravityObjet> listeObjets = new ArrayList<GravityObjet>();
		ParamSettingsManager param = ParamSettingsManager.getParamSettingsUser();
		Double poidLune = (Double)param.getParam(EnumParam.poidLune).getValue();
		int tailleLune = 3;

		for(int i = 0 ; i < nbObjets ; i++) {
			double maxForce = ((poidLune*poidLune)/tailleFenetre)*2;
			Vecteur directionLune = new Vecteur(new Position((Math.random()-0.5)*maxForce,(Math.random()-0.5)*maxForce));
			Position positionLune = new Position(Math.random()*tailleFenetre, Math.random()*tailleFenetre);
			
			GravityObjet lune = new Planete(tailleLune , poidLune , tailleFenetre , NameManager.getName());
			lune.setDirection(directionLune);
			lune.setPosition(positionLune);
			
			listeObjets.add(lune);
		}

		return(listeObjets);
	}
	// plusieurs lunes avec un soleil
	public static List<GravityObjet> nuageOrbite1soleil(int nbetoiles , int tailleFenetre){
		ParamSettingsManager param = ParamSettingsManager.getParamSettingsUser();
		ArrayList<GravityObjet> listeObjets = new ArrayList<GravityObjet>();

		double distanceMinSoleil = ((tailleFenetre+0.0D)/2)/3;
		double distanceMaxSoleil = (tailleFenetre+0.0D)/3;
		Double poidSoleil = (Double)param.getParam(EnumParam.poidTerre).getValue();
		int tailleSoleil = 14;
		Position positionSoleil = new Position(tailleFenetre/2,tailleFenetre/2);
		Double poidLune = (Double)param.getParam(EnumParam.poidLune).getValue();
		int tailleLune = 4;

		
		Planete soleil = new Planete(tailleSoleil , poidSoleil , tailleFenetre , "soleil");
		soleil.setPosition(positionSoleil);
		soleil.setUnMovavle(true);
		listeObjets.add(soleil);
		
		for(int i = 0 ; i < nbetoiles ; i++) {
			Position positionLune = getPositionDonut(soleil.getPosition(), distanceMaxSoleil, distanceMinSoleil);
			Position positionCentre = new Position(soleil.getPosition().getX(),soleil.getPosition().getY());
			Vecteur dirrectionLune = ConcreteGravityPhysique.calculateOrbitalTrajectory(true , positionLune , poidLune , positionCentre , soleil.getPoid());
			Planete lune = new Planete(tailleLune , poidLune , tailleFenetre , NameManager.getName());
			lune.setDirection(dirrectionLune);
			lune.setPosition(positionLune);
			listeObjets.add(lune);
		}
		return(listeObjets);
	}	
	// plusieurs lunes avec deux soleils
	public static List<GravityObjet> nuageOrbite2soleil(int nbObjet , int tailleFenetre){
		ArrayList<GravityObjet> listeObjets = new ArrayList<GravityObjet>();
		ParamSettingsManager param = ParamSettingsManager.getParamSettingsUser();
		double distanceMinSoleil = ((tailleFenetre+0.0D)/2)/3;
		double distanceMaxSoleil = (tailleFenetre+0.0D)/3;
		
		// soleils
		double poidSoleil = (Double)param.getParam(EnumParam.poidTerre).getValue();
		int tailleSoleil = 7;
		Position positionSoleil1 = new Position(tailleFenetre/2-25, tailleFenetre/2);
		Position positionSoleil2 = new Position(tailleFenetre/2+25, tailleFenetre/2);
		Position positionCentre = Position.centralPosition(positionSoleil1, positionSoleil2);
		Planete soleil1 = new Planete(tailleSoleil , poidSoleil , tailleFenetre , "soleil1");
		soleil1.setPosition(positionSoleil1);
		Planete soleil2 = new Planete(tailleSoleil , poidSoleil , tailleFenetre , "soleil2");
		soleil2.setPosition(positionSoleil2);
		Vecteur[] directionsSoleils = ConcreteGravityPhysique.calculateOrbitalTrajectory2ObjectsSystem(true, soleil1, soleil2);
		soleil1.setDirection(directionsSoleils[0]);
		soleil2.setDirection(directionsSoleils[1]);
		listeObjets.add(soleil1);
		listeObjets.add(soleil2);
		
		// lunes
		Double poidLune = (Double)param.getParam(EnumParam.poidLune).getValue();
		int tailleLunes = 3;
		for(int i = 0 ; i < nbObjet ; i++) {
			Position positionLune = getPositionDonut(positionCentre, distanceMaxSoleil, distanceMinSoleil);
			Vecteur dirrectionLune = ConcreteGravityPhysique.calculateOrbitalTrajectory(true , positionLune , poidLune , positionCentre , poidSoleil*2);
			Planete luneObese = new Planete(tailleLunes , poidLune , tailleFenetre , NameManager.getName());
			luneObese.setDirection(dirrectionLune);
			luneObese.setPosition(positionLune);
			listeObjets.add(luneObese);
		}
		
		return(listeObjets);
	}
	
	// permet d'obtenir des positions en forme de donut centrée sur un point
	private static Position getPositionDonut(Position centreDonut , double rayonMax , double rayonMin) {
		double x = (Math.random()-0.5)*2*rayonMax;
		double y = (Math.random()-0.5)*2*rayonMax;
		Position lune = new Position(centreDonut.getX()+x, centreDonut.getY()+y);
		
		while(Position.getDistance(lune, centreDonut) < rayonMin || Position.getDistance(lune, centreDonut) > rayonMax) {
			x = (Math.random()-0.5)*2*rayonMax;
			y = (Math.random()-0.5)*2*rayonMax;
			lune = new Position(centreDonut.getX()+x, centreDonut.getY()+y);
		}
		return(lune);
	}
}