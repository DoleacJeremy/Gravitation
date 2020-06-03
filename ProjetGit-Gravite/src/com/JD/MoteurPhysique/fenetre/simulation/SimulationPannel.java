package com.JD.MoteurPhysique.fenetre.simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import com.JD.MoteurPhysique.core.GroupeGravityObjet;
import com.JD.MoteurPhysique.fenetre.param.ParamFrame;

@SuppressWarnings("serial")
public class SimulationPannel extends JPanel{
	private SimulationProcessable concreteSimulation;
	
	public SimulationPannel( int size) {
		this.setPreferredSize(new Dimension(size,size));
		this.setSize(new Dimension(size,size));
		this.setBackground(Color.BLACK);
		
		this.concreteSimulation = new GroupeGravityObjet();	
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.concreteSimulation.drawSimulation(g);
	}
	
	public void conputeSimulation() {
		new Thread(this.concreteSimulation).start();
		while(!this.concreteSimulation.estSimulationFinie()) {
			this.repaint();
			try {Thread.sleep((int)(1000F/ParamFrame.FPS));} catch (InterruptedException e) {}
		}
	}
}