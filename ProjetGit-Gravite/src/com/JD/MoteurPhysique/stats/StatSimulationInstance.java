package com.JD.MoteurPhysique.stats;

import java.util.ArrayList;
import java.util.List;

import com.JD.MoteurPhysique.core.GravityObjet;


public class StatSimulationInstance {
	private ArrayList<Integer> listeNBObjetsPhysique = new ArrayList<Integer>();
	private ArrayList<GravityObjet> listeObjetsPhysique = new ArrayList<GravityObjet>();
	private ArrayList<Integer> listeTempsProcessTourMS = new ArrayList<Integer>();
	private ArrayList<Integer> listedesToursSave = new ArrayList<Integer>();
	
	private boolean addValues;
	
	// getters
	public int getLastNBObjetsPhysique() {
		return(this.listeNBObjetsPhysique.get(this.listeNBObjetsPhysique.size()-1));
	}
	public int getLastMilis() {
		return(this.listeTempsProcessTourMS.get(this.listeTempsProcessTourMS.size()-1));
	}
	public int getLastTour() {
		return(this.listedesToursSave.get(this.listedesToursSave.size()-1));
	}

	// getters
	public List<Integer> getListNBObjetsPhysique() {
		return(this.listeNBObjetsPhysique);
	}
	public List<Integer> getListMilis() {
		return(this.listeTempsProcessTourMS);
	}
	public List<Integer> getListTour() {
		return(this.listedesToursSave);
	}
	public List<GravityObjet> getListPositionsDepart() {
		return(this.listeObjetsPhysique);
	}

	// setters
	public void setMilis(int secondes) {
		if(this.addValues)
			this.listeTempsProcessTourMS.add(secondes);
	}
	public void setTours(int tours) {
		if(this.addValues)
			this.listedesToursSave.add(tours);
	}
	public void setPositionsDepart(List<GravityObjet> positions) {
		this.listeObjetsPhysique = new ArrayList<GravityObjet>();
		this.listeObjetsPhysique.addAll(positions);
	}

	//ajoute un tour seulement si les valeurs sont différentes du tour précédent
	public void setCampValues(int nbStars) {
		this.addValues = true;
		boolean stagnation;
		if(!this.listeNBObjetsPhysique.isEmpty()) {
			int lastIndex = this.listeNBObjetsPhysique.size()-1;
			stagnation = this.listeNBObjetsPhysique.get(lastIndex) == nbStars;
			if(stagnation)
				this.addValues = false;
		}
		if(this.addValues) {
			this.listeNBObjetsPhysique.add(nbStars);
		}
	}

	// vérifie si le tour passé en paramétre est bien le dernier enregistré
	public boolean isLastTurn(int turn) {
		return(this.listedesToursSave.get(this.listedesToursSave.size()-1) == turn);
	}
	public String getLog() {
		String retour = "[tour " + this.getLastTour()+"]   ";
		retour += "["+this.getLastMilis()+" ms]   ";
		retour += "stars : " + this.getLastNBObjetsPhysique();
		return(retour);
	}
	
}