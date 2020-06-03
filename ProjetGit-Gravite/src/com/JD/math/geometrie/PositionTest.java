package com.JD.math.geometrie;

import static org.junit.Assert.*;

import org.junit.Test;

public class PositionTest {
	
	@Test
	public void testGetDistanceXaxis() {
		Position positionDepart = new Position(0,0);
		Position positionTemp = new Position(0,0);
		
		double[] distances = {
			0,
			14,
			3.37888,
			-45,
			42,
			Math.PI
		};
		
		double totalDistance = 0;
		for(double distance : distances) {
			totalDistance += distance;
			positionTemp.setX(positionTemp.getX()+distance);
			assertEquals(Position.getDistance(positionDepart, positionTemp),(Double)Math.abs(totalDistance));
		}
	}

	@Test
	public void testGetDistanceYaxis() {
		Position positionDepart = new Position(0,0);
		Position positionTemp = new Position(0,0);
		
		double[] distances = {
			0,
			14,
			3.37888,
			-45,
			42,
			Math.PI
		};
		
		double totalDistance = 0;
		for(double distance : distances) {
			totalDistance += distance;
			positionTemp.setY(positionTemp.getY()+distance);
			assertEquals(Position.getDistance(positionDepart, positionTemp),(Double)Math.abs(totalDistance));
		}
	}

	@Test
	public void testGetDistance() {
		Position pointUn = new Position(2,3);
		Position pointDeux = new Position(0,0);
		Double distance = 3.605551275463989D;
		assertEquals(Position.getDistance(pointUn,pointDeux) , distance);
		assertEquals((Double)(0.0D) , Position.getDistance(pointUn,pointUn));
		
		
		pointUn = new Position(1,1);
		pointDeux = new Position(0,0);
		Position pointTrois = new Position(-1,-1);
		Position pointQuatre = new Position(2,2);
		assertEquals(Position.getDistance(pointUn,pointDeux) , Position.getDistance(pointDeux,pointTrois));
		assertEquals(Position.getDistance(pointUn,pointTrois) , Position.getDistance(pointDeux,pointQuatre));
		

		pointUn = new Position(1.11D,1.11D);
		pointDeux = new Position(2.29D,3.45D);
		distance = 2.6206869328479505D;
		assertEquals(Position.getDistance(pointUn,pointDeux),distance);
		
		
		pointUn = new Position(2,3);
		pointDeux = new Position(-1,-1);
		pointTrois = new Position(2,3);
		pointQuatre = new Position(-2,-3);
		assertNotEquals(pointUn,pointDeux);
		assertEquals(pointUn,pointTrois);
		assertNotEquals(pointUn,pointQuatre);
	}
	
	@Test
	public void test_centralPosition() {
		Position[][] points = {
			{new Position(0,0),new Position(1,1)},
			{new Position(1,1),new Position(-1,-1)},
			{new Position(0,0),new Position(0,0)},
			{new Position(0,10),new Position(0,5)},
			{new Position(10,0),new Position(5,0)}
		};
		
		Position[] solutionAttendu = {
			new Position(0.5,0.5),
			new Position(0,0),
			new Position(0,0),
			new Position(0.0D,7.5D),
			new Position(7.5D,0.0D)
		};
		
		for(int i = 0 ; i < points.length ; i++) {
			Position pointObtenue = Position.centralPosition(points[i][0], points[i][1]);
			
			assertEquals(pointObtenue,solutionAttendu[i]);
		}
	}
	
	@Test
	public void test_getSens() {
		Position centre = new Position(0,0);
		Position A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P;
			A = new Position(-8,0);
			B = new Position(-7,4);
			C = new Position(-5.656854249492381D,5.656854249492381D);
			D = new Position(-4,7);
			E = new Position(0,8);
			F = new Position(4,7);
			G = new Position(5.656854249492381D,5.656854249492381D);
			H = new Position(7,4);
			I = new Position(8,0);
			J = new Position(7,-4);
			K = new Position(5.656854249492381D,-5.656854249492381D);
			L = new Position(4,-7);
			M = new Position(0,-8);
			N = new Position(-4,-7);
			O = new Position(-5.656854249492381D,-5.656854249492381D);
			P = new Position(-7,-4);
		
		
		Position[][] couplePointHorraire = {
				{P,B},
				{H,J},
				{D,F},
				{L,N},

				{B,D},
				{F,H},
				{J,L},
				{N,P},

				{A,B},
				{E,F},
				{I,J},
				{M,N},

				{D,E},
				{H,I},
				{L,M},
				{P,A},

				{O,C},
				{C,G},
				{G,K},
				{K,O},
		};
		
		Position positionObtenue = null;
		boolean horraire = true;
		for(Position[] couplePoint : couplePointHorraire) {
			//horaire
			positionObtenue = Position.getSens(couplePoint[0], couplePoint[1], centre, horraire);
			assertEquals(couplePoint[1], positionObtenue);
			positionObtenue = Position.getSens(couplePoint[1], couplePoint[0], centre, horraire);
			assertEquals(couplePoint[1], positionObtenue);
			
			
			//antihoraire
			positionObtenue = Position.getSens(couplePoint[0], couplePoint[1], centre, !horraire);
			assertEquals(couplePoint[0], positionObtenue);
			positionObtenue = Position.getSens(couplePoint[1], couplePoint[0], centre, !horraire);
			assertEquals(couplePoint[0], positionObtenue);
		}
	}

	
	
	@Test
	public void test_translationde_juste() {
		Position point1 = new Position(0,0);
		Position resultat = new Position(1,1);
		Vecteur vecteur = new Vecteur(resultat);
		
		point1.addVector(vecteur);
		assertEquals(resultat , point1);
		
		point1 = new Position(0,0);
		resultat = new Position(2,3);
		vecteur = new Vecteur(resultat);

		point1.addVector(vecteur);
		assertEquals(resultat , point1);
	}
	
	@Test
	public void test_translationde_pasJuste() {
		Position point1 = new Position(0,0);
		Position resultat = new Position(1,1);
		Vecteur vecteur = new Vecteur(new Position(2,1));
		
		point1.addVector(vecteur);
		assertNotEquals(resultat , point1);
		
		point1 = new Position(0,0);
		resultat = new Position(2,3);
		vecteur = new Vecteur(new Position(2,1));

		point1.addVector(vecteur);
		assertNotEquals(resultat , point1);
	}
	
	@Test
	public void test_pastranslationde() {
		Position point1 = new Position(0,0);
		Position resultat = new Position(0,0);
		Vecteur vecteur = new Vecteur(resultat);
		
		point1.addVector(vecteur);
		assertEquals(resultat , point1);
	}
	
	@Test
	public void test_translationdeNegative_pasJuste() {
		Position point1 = new Position(0,0);
		Position resultat = new Position(-1,-1);
		Vecteur vecteur = new Vecteur(new Position(-2,-1));
		
		point1.addVector(vecteur);
		assertNotEquals(resultat , point1);
		
		point1 = new Position(0,0);
		resultat = new Position(-2,-3);
		vecteur = new Vecteur(new Position(-2,-1));

		point1.addVector(vecteur);
		assertNotEquals(resultat , point1);
	}
	
	@Test
	public void test_translationdeNegative_juste() {
		Position point1 = new Position(0,0);
		Position resultat = new Position(-1,-1);
		Vecteur vecteur = new Vecteur(resultat);
		
		point1.addVector(vecteur);
		assertEquals(resultat , point1);
		
		point1 = new Position(0,0);
		resultat = new Position(-2,-3);
		vecteur = new Vecteur(resultat);

		point1.addVector(vecteur);
		assertEquals(resultat , point1);
	}
	
	@Test
	public void test_translationdepas0_juste() {
		Position point1 = new Position(1,1);
		Position resultat = new Position(2,11);
		Vecteur vecteur = new Vecteur(new Position(1,10));
		
		point1.addVector(vecteur);
		assertEquals(resultat , point1);
		
		point1 = new Position(1,1);
		resultat = new Position(0,0);
		vecteur = new Vecteur(new Position(-1,-1));

		point1.addVector(vecteur);
		assertEquals(resultat , point1);
	}	
}