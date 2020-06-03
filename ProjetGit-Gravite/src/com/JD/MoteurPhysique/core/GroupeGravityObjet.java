package com.JD.MoteurPhysique.core;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.JD.MoteurPhysique.fenetre.param.EnumParam;
import com.JD.MoteurPhysique.fenetre.param.ParamFrame;
import com.JD.MoteurPhysique.fenetre.simulation.SimulationProcessable;
import com.JD.MoteurPhysique.manager.ParamSettingsManager;
import com.JD.MoteurPhysique.manager.StringManager;
import com.JD.MoteurPhysique.stats.StatSimulationInstance;
import com.JD.MoteurPhysique.stats.StatsManager;
import com.JD.math.geometrie.Position;
import com.JD.math.geometrie.Vecteur;

public class GroupeGravityObjet implements SimulationProcessable{
	private ArrayList<GravityObjet> listePhysicObject;
	private ArrayList<GravityObjet> listeGraphicsPhysicObject;
	private StatSimulationInstance singletonStat;
	private int tailleFenetre;
	private boolean finSimulaton = false;
	private int tour;
	private int delaieMS;
	private int compteur;
	private int valeurMaxCompter;
	private long clefStats;
	
	
	// constructeur
	public GroupeGravityObjet() {
		this.listePhysicObject = new ArrayList<GravityObjet>();
		this.listeGraphicsPhysicObject = new ArrayList<GravityObjet>();
		ParamSettingsManager paramSettingsManager = ParamSettingsManager.getParamSettingsUser();
		
		
		//permet de gérer plusieur instances de statistique pour chaque fenetre
		Date date = new Date();
		this.clefStats = date.getTime();
		this.singletonStat = StatsManager.getSingletonStat().getStats(this.clefStats);
		this.singletonStat.setPositionsDepart(this.listePhysicObject);
		this.tour = 0;
		//permet de mettre à jour l'affichage à une valeur définie
		this.valeurMaxCompter = Math.max(1000/ParamFrame.FPS-this.delaieMS,0);
		this.compteur = this.valeurMaxCompter;
		
		int nbEtoiles = (Integer)paramSettingsManager.getParam(EnumParam.nbObject).getValue();
		this.delaieMS = (Integer)paramSettingsManager.getParam(EnumParam.delay).getValue();
		this.tailleFenetre = (Integer)paramSettingsManager.getParam(EnumParam.windowsSize).getValue();
		String configuration = (String)paramSettingsManager.getParam(EnumParam.configuration).getValue();

		//selectionne le type de simulation (1 soleil , 2 soleils ou mouvement anarchique)
		if(configuration.equals("1"))
			this.listePhysicObject.addAll(PositionementObjet.nuageOrbite1soleil(nbEtoiles , this.tailleFenetre));
		else
			if(configuration.equals("2"))
				this.listePhysicObject.addAll(PositionementObjet.nuageOrbite2soleil(nbEtoiles , this.tailleFenetre));
			else
				this.listePhysicObject.addAll(PositionementObjet.nuageOrbite(nbEtoiles, this.tailleFenetre));
		
		
		System.out.println("[GroupeObjetPhysique>67]   génération finie");
	}
	
	
	
	
	/*         _             
		  __ _| | __ _  ___  
		 / _` | |/ _` |/ _ \ 
		| (_| | | (_| | (_) |
		 \__,_|_|\__, |\___/ 
		         |___/     
	*/
	// calcule un tour entier sur touts les objets présents dans le groupe
	public void calculeUnTour() {
		Date debut = new Date();
		//fusionne les objets qui se touchent
		this.colisions();
		//bouge les objets qui restent
		this.bougerObjets();
		//met a jour l'affichage chaque this.valeurMaxCompter tours
		if(this.compteur >= this.valeurMaxCompter) {
			this.compteur = 0;
			//création d'une autre liste pour les ConcurentException lorsque l'affichage accéde a la liste en méme temps que le programme
			this.listeGraphicsPhysicObject = new ArrayList<GravityObjet>();
			for(GravityObjet p : this.listePhysicObject) {
				this.listeGraphicsPhysicObject.add((GravityObjet)p.clone());
			}
		}
		Date fin = new Date();
		
		//statistiques et affichage 
		this.singletonStat.setMilis((int)(fin.getTime()-debut.getTime()));
		this.singletonStat.setTours(this.tour);
		if(this.singletonStat.isLastTurn(this.tour))
			System.out.println("[GroupeObjetPhysique:102]   "+this.singletonStat.getLog());
		this.tour++;
		this.compteur++;
	}
	// fait bouger tous les objets en calculant leur atirance gravitationelle
	private void bougerObjets() {
		this.singletonStat.setCampValues(this.listePhysicObject.size());
		
		// calcule et sauvegarde le prochain mouvement
		for(GravityObjet cell : this.listePhysicObject) {
			cell.chooseDirection(this.listePhysicObject);
		}

		// applique le mouvement précédemment calculé
		for(int i = 0 ; i < this.listePhysicObject.size() ; i++) {
			this.listePhysicObject.get(i).moveToDirection();
			if(this.listePhysicObject.get(i).isRemovable()) {
				this.listePhysicObject.remove(i);
				i--;
			}
		}
		
		// fin si il reste 1 seul objet
		this.finSimulaton = this.listePhysicObject.size() == 1;
	}
	// detecte et regroupe les objets qui se touchent
	private void colisions() {
		ArrayList<Integer> listeObjetsARetirer = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> listeObjets = new ArrayList<ArrayList<Integer>>();
		ArrayList<GravityObjet> isteObjetsAAjouter = new ArrayList<GravityObjet>();
		
		// récupére tout les groupes d'étoiles qui vont etre fusionné
		for (int i = 0; i < this.listePhysicObject.size() ; i++) {
			if(!listeObjetsARetirer.contains(i)) {
				// cherche les étoiles qui se touchent
				ArrayList<Integer> objetGroupeTouche = this.getGroupeColision(i);
				if(objetGroupeTouche.size() > 1) {
					listeObjets.add(objetGroupeTouche);
					listeObjetsARetirer.addAll(objetGroupeTouche);
				}
			}
			
		}

		// fusionne et ajoute le resultat des fusions
		isteObjetsAAjouter.addAll(this.regroupementCollision(listeObjets));
		
		// retire les groupes qui posent probléme
		listeObjetsARetirer = new ArrayList<>(new HashSet<>(listeObjetsARetirer));
		Collections.sort(listeObjetsARetirer);
		for(int starIndex = listeObjetsARetirer.size()-1 ; starIndex >= 0 ; starIndex--) {
			int indexARetirer = listeObjetsARetirer.get(starIndex);
			this.listePhysicObject.remove(indexARetirer);
		}

		//ajout pour de vrai
		this.listePhysicObject.addAll(isteObjetsAAjouter);
	}
	// fusionne les objets qui se touchent
	private ArrayList<GravityObjet> regroupementCollision(ArrayList<ArrayList<Integer>> listeGroupeObjets){
		ArrayList<GravityObjet> listeObjetsARetirer = new ArrayList<GravityObjet>();
		for(ArrayList<Integer> listeGroupe : listeGroupeObjets) {
			
			if(this.estBougeable(listeGroupe)) {
				// dans le cas des objets qui ne peuvent pas bouger
				Double poid = 0D;
				String nom = "";
				Double tailleNouvelObjet = 0D;
				Position positionCentrale = null;
				
				// récupére le poids de l'objet le plus lourd
				double poidMax = 0D;
				for(int indexObjet : listeGroupe) {
					if(poidMax < this.listePhysicObject.get(indexObjet).getPoid()) {
						poidMax = this.listePhysicObject.get(indexObjet).getPoid();
					}
				}
				
				// obtiens tous les paramétres des objets pour crée les paramétres de l'objet final
				for(int indexObjet : listeGroupe) {
					Planete planete = (Planete)this.listePhysicObject.get(indexObjet);
					if(planete.getPoid()/poidMax > 0.005D) {
						if(planete.getNom().contains("[")) {
							nom += planete.getNom();
						}else {
							nom += "["+planete.getNom()+"]";
						}
					}
					//si l'objet ne peut pas bouger on garde sa position
					if(planete.estBougeable())
						positionCentrale = planete.getPosition();
					poid += planete.getPoid();
					tailleNouvelObjet = Math.sqrt(tailleNouvelObjet*tailleNouvelObjet+planete.getSize()*planete.getSize());
				}
				
				//creation du nouvel objet
				GravityObjet objetFinal = new Planete(tailleNouvelObjet.intValue(), poid, this.tailleFenetre , nom);
				objetFinal.setPosition(positionCentrale);
				objetFinal.setUnMovavle(true);
				listeObjetsARetirer.add(objetFinal);
			}else {
				// dans le cas des objets qui peuvent bouger
				double x = 0;
				double y = 0;
				Double poid = 0D;
				String nom = "";
				Double tailleNouvelObjet = 0D;
				Vecteur direction = new Vecteur(new Position(0,0));
				ArrayList<Position> newTrace = null;

				// récupére le poids de l'objet le plus lourd et la trace la plus longue 
				int maxTrace = 0;
				double poidMax = 0D;
				for(int indexObjet : listeGroupe) {
					if(poidMax < this.listePhysicObject.get(indexObjet).getPoid()) {
						poidMax = this.listePhysicObject.get(indexObjet).getPoid();
					}
					if(maxTrace < this.listePhysicObject.get(indexObjet).getTrace().size()-1)
						maxTrace = this.listePhysicObject.get(indexObjet).getTrace().size()-1;
				}
				

				// obtiens tous les paramétres des objets pour crée les paramétres de l'objet final
				for(int indexObjet : listeGroupe) {
					Planete planete = (Planete)this.listePhysicObject.get(indexObjet);
					x += planete.getPosition().getX()*planete.getPoid();
					y += planete.getPosition().getY()*planete.getPoid();
					if(planete.getPoid()/poidMax > 0.005D) {
						if(planete.getNom().contains("[")) {
							nom += planete.getNom();
						}else {
							nom += "["+planete.getNom()+"]";
						}
					}
					if(planete.getPoid() == poidMax)
						newTrace = (ArrayList<Position>) planete.getTrace();
					poid += planete.getPoid();
					tailleNouvelObjet = Math.sqrt(tailleNouvelObjet*tailleNouvelObjet+planete.getSize()*planete.getSize());
					
					planete.getDirection().setLongueur(planete.getDirection().getLongueur()*(planete.getPoid()/poidMax));
					direction.translater(planete.getDirection());
				}
				direction.setLongueur(direction.getLongueur()/listeGroupe.size());
				Position positionCentrale = new Position(x/poid, y/poid);

				//creation du nouvel objet
				GravityObjet objetFinal = new Planete(tailleNouvelObjet.intValue(), poid, this.tailleFenetre , nom);
				objetFinal.setPosition(positionCentrale);
				objetFinal.setDirection(direction);
				objetFinal.setTrace(newTrace);
				listeObjetsARetirer.add(objetFinal);
			}
		}
		
		return(listeObjetsARetirer);
	}
	// permet d'obtenir tous les objets qui touchent l'objet correspondant a l'indexe fourni
	private ArrayList<Integer> getGroupeColision(int indexObjet){
		ArrayList<Integer> listeGroupeObjets = new ArrayList<Integer>();
		GravityObjet c = this.listePhysicObject.get(indexObjet);
		
		for(int i = 0 ; i < this.listePhysicObject.size() ; i++) {
			if(i != indexObjet && c.touche(this.listePhysicObject.get(i))) {
				listeGroupeObjets.add(i);
			}
		}
		if(!listeGroupeObjets.isEmpty())
			listeGroupeObjets.add(indexObjet);
		
		return(listeGroupeObjets);
	}
	
	
	
	/*	             _            
		  __ _ _   _| |_ _ __ ___ 
		 / _` | | | | __| '__/ _ \
		| (_| | |_| | |_| | |  __/
		 \__,_|\__,_|\__|_|  \___|
	*/
	// permet de savoir si l'objet peut bouger ou pas 
	public boolean estBougeable(List<Integer> listeGroupeObjets) {
		for(int indexObjet : listeGroupeObjets)
			if(this.listePhysicObject.get(indexObjet).estBougeable())
				return(true);
		return(false);
	}
	// permet de savoir si la simulation est finie
	public boolean estSimulationFinie() {
		return(this.finSimulaton);
	}
	// permet de finir la simulation manuellement
	public void finirSimulation() {
		this.finSimulaton = true;
	}
	// lance le thread et l'algo
	@Override
	public void run() {
		while(!this.finSimulaton) {
			this.calculeUnTour();
			try {Thread.sleep(this.delaieMS);} catch (InterruptedException e) {}
		}
		this.calculeUnTour();
	}
	
	
	
	
	/*          __  __ _      _                      
		  __ _ / _|/ _(_) ___| |__   __ _  __ _  ___ 
		 / _` | |_| |_| |/ __| '_ \ / _` |/ _` |/ _ \
		| (_| |  _|  _| | (__| | | | (_| | (_| |  __/
		 \__,_|_| |_| |_|\___|_| |_|\__,_|\__, |\___|
		                                  |___/      
	 */
	
	// dessine l'objet sur une fenetre
	public void drawSimulation(Graphics g) {
		this.dessiner(g, this.listeGraphicsPhysicObject);
	}
	// methode exterieur pour dessiner en utilisant la récurrence a cause des ConcurrentModificationException
	private void dessiner(Graphics g , ArrayList<GravityObjet> cells) {
		try {
			for(GravityObjet c : cells) {
				c.drawCell(g);
			}
			StringManager.drawStringOutLined(g,"Tour : "+this.tour , new Position(0,0),Color.WHITE,Color.BLACK);
			
		}catch(ConcurrentModificationException e) {
			try{Thread.sleep(100);}catch(Exception eSleep){}
			this.dessiner(g, cells);
		}
	}
}