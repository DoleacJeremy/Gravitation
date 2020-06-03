package com.JD.MoteurPhysique.fenetre.simulation;

import java.awt.Graphics;

public interface SimulationProcessable extends Runnable {
	public void drawSimulation(Graphics g);
	public void calculeUnTour();
	public boolean estSimulationFinie();
	public void finirSimulation();
}