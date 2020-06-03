package com.JD.MoteurPhysique.fenetre.simulation;

import java.awt.Dimension;
import javax.swing.JFrame;

import com.JD.MoteurPhysique.fenetre.param.EnumParam;
import com.JD.MoteurPhysique.fenetre.param.ParamFrame;
import com.JD.MoteurPhysique.manager.ParamSettingsManager;

@SuppressWarnings("serial")
public class SimulationFrame extends JFrame implements Runnable{
	private SimulationPannel panel;
	
	public SimulationFrame() {
		super();
		ParamSettingsManager manager = ParamSettingsManager.getParamSettingsUser();
		int tailleFenetre = (int) manager.getParam(EnumParam.windowsSize).getValue();
		int nbObject = (int) manager.getParam(EnumParam.nbObject).getValue();
		
		
		this.panel = new SimulationPannel(tailleFenetre);
		this.setContentPane(this.panel);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setTitle(ParamFrame.NOMPROJET+" - simulation - "+nbObject);
		this.setSize(new Dimension(tailleFenetre+6,tailleFenetre+29));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public void changeInfoFin() {
		ParamSettingsManager manager = ParamSettingsManager.getParamSettingsUser();
		int nbObject = (int) manager.getParam(EnumParam.nbObject).getValue();
		this.setTitle(ParamFrame.NOMPROJET+" - simulation - "+nbObject+" - fini");
	}

	@Override
	public void run() {
		this.panel.conputeSimulation();
		this.changeInfoFin();
	}
}