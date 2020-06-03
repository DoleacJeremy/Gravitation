package com.JD.math.geometrie;

import java.util.ArrayList;
import java.util.List;

import com.JD.math.general.Functions;

public class FunctionGeometrie {
	
	private FunctionGeometrie() {}
	
	// renvoi les positions des points qui correspondent au intersection entre le cercle et la droite passé en paramétre
	public static List<Position> getIntersection(Droite d , Cercle c) {
		ArrayList<Position> positions = new ArrayList<Position>();
		
		//dans le cas ou la droite est une constante en X
		if(d.isConstantX()) {
			if(d.getP1().getX() > c.getCentre().getX()+c.getRayon() || d.getP1().getX() < c.getCentre().getX()-c.getRayon())
				return(positions);
			double a = c.getCentre().getX()-d.getP1().getX();
			double oppose = Math.sqrt(c.getRayon()*c.getRayon()-a*a);
			positions.add(new Position(d.getP1().getX(),c.getCentre().getY()-oppose));
			positions.add(new Position(d.getP1().getX(),c.getCentre().getY()+oppose));
			return(positions);
		}
		
		//variables de base la droite
		double droiteA = d.getA();
		double droiteB = d.getB();
		//variables de base du cercle
		double cercleR = c.getRayon();
		double cercleX = c.getCentre().getX();
		double cercleY = c.getCentre().getY();
		
		//delta
		double deltaA = (1+droiteA*droiteA);
		double deltaB = (-2*cercleX+2*droiteA*droiteB-2*droiteA*cercleY);
		double deltaC = cercleX*cercleX+droiteB*droiteB-2*droiteB*cercleY+cercleY*cercleY-cercleR*cercleR;

		//calcul delta et utilisation de l'equation de la droite car elle est plus simple que celle du cercle
		ArrayList<Double> solutionsX = (ArrayList<Double>) Functions.calculDelta(deltaA, deltaB, deltaC);
		for(double x : solutionsX)
			positions.add(new Position(x, droiteA*x+droiteB));
		
		return(positions);
	}
}