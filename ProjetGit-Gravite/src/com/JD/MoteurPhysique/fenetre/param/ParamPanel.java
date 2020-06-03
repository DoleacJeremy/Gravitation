package com.JD.MoteurPhysique.fenetre.param;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import com.JD.MoteurPhysique.fenetre.simulation.SimulationFrame;
import com.JD.MoteurPhysique.manager.EnumImages;
import com.JD.MoteurPhysique.manager.ImageManager;
import com.JD.MoteurPhysique.manager.ParamSettingsManager;


@SuppressWarnings("serial")
public class ParamPanel extends JPanel implements ActionListener{
	private HashMap<EnumParam,JTextArea> mapJPanelsParam = new HashMap<EnumParam,JTextArea>();
	private JButton boutton;
	private BufferedImage background;
	
	

	public ParamPanel() {
		EnumParam.setUpTypes();
		
		//image de fond
		ImageManager imageManager = new ImageManager();
		this.background = imageManager.getImage(EnumImages.background_param);
		
		// ajoute une ligne pour chaque entré de l'énumeration
		for(EnumParam param : EnumParam.values())
			this.add(this.getPanelParam(param));
		
		// vide pour mettre le bouton next en bas
		int tailleEspaceY = 320-this.mapJPanelsParam.size()*30-(this.mapJPanelsParam.size()+1)*5;
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setPreferredSize(new Dimension(390,tailleEspaceY));
		this.add(panel);
		
		// bouton next en bas
		this.boutton = new JButton();
		this.boutton .setPreferredSize(new Dimension(100,30));
		this.boutton.setText("next");
		this.boutton.addActionListener(this);
		this.add(this.boutton);
	}
	
	
	// génére un jpanel pour chaque paramétre
	public JPanel getPanelParam(EnumParam param) {
		JPanel panelParam = new JPanel();

		// texte information (gauche)
		JTextPane infoTaille = new JTextPane();
		infoTaille.setText(EnumParam.getInfo(param));
		infoTaille.setPreferredSize(new Dimension(225,20));
		infoTaille.setEditable(false);
		
		// zone input (centre)
		JTextArea texteTaille = new JTextArea();
		texteTaille.setPreferredSize(new Dimension(75,20));
		texteTaille.setText(EnumParam.getDefautValeurs(param).getValue().toString());

		// type de valeur (droite)
		JTextArea infoType = new JTextArea();
		infoType.setText(EnumParam.getType(param).toString());
		infoType.setPreferredSize(new Dimension(50,20));
		infoType.setEditable(false);
		
		// ajout au panel
		panelParam.setPreferredSize(new Dimension(370,30));
		panelParam.add(infoTaille);
		panelParam.add(texteTaille);
		panelParam.add(infoType);
		// ajout a la liste pour récupéré la valeur plus tard quand l'utilisateur appuy sur next
		this.mapJPanelsParam.put(param, texteTaille);
		
		return(panelParam);
	}
	

	// comportement du clic sur le bouton
	@Override
	public void actionPerformed(ActionEvent arg0) {

		boolean validationLancement = true;

		// récupération des valeurs de chaque paramétre
		for(EnumParam param : EnumParam.values()) {
			this.mapJPanelsParam.get(param).setBackground(Color.GREEN);
			String valeueTextuelle = this.mapJPanelsParam.get(param).getText();

			if(ParamVariable.isParamValid(param, valeueTextuelle)) {
				this.insertParamValue(param, valeueTextuelle);
			}else {
				validationLancement = false;
				this.mapJPanelsParam.get(param).setBackground(Color.RED);
				System.out.println("[ParamPanel:96] paramétre \""+param+"\" invalide : \""+valeueTextuelle+"\"");
			}
			
		}
		
		// lancement de la simulation si les valeurs sont bonnes
		if(validationLancement) {
			SimulationFrame fenetre = new SimulationFrame();
			new Thread(fenetre).start();

			for(EnumParam param : EnumParam.values()) {
				this.mapJPanelsParam.get(param).setBackground(Color.WHITE);
			}
		}
	}
	
	// convertis les valeurs des différents paramétres
	private void insertParamValue(EnumParam param , String valeurTextuelle) {
		ParamSettingsManager paramSetting = ParamSettingsManager.getParamSettingsUser();
		EnumSuportedParamType paramType = EnumParam.getType(param);

		ParamVariable<?> valeurParam = null; 
		switch(paramType) {
			case nombre :
				valeurParam = new ParamVariable<Integer>(Integer.parseInt(valeurTextuelle));
				break;
			case texte :
				valeurParam = new ParamVariable<String>(valeurTextuelle);
				break;
			case virgule :
				valeurParam = new ParamVariable<Double>(Double.valueOf(valeurTextuelle));
				break;
		}

		paramSetting.setParam(param, valeurParam);
	}
	

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.background, 0, 0, this.getWidth(), this.getHeight(), null);
	}	
}