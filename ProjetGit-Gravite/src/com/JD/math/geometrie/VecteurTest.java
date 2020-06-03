package com.JD.math.geometrie;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class VecteurTest {

	@Test
	public void test_longeur() {
		Vecteur[] vecteurs = {
				new Vecteur(new Position(0,0)),
				new Vecteur(new Position(0,1)),
				new Vecteur(new Position(1,1)),
				new Vecteur(new Position(-1,-2)),
				new Vecteur(new Position(1,2))
			};
			double[] longeurs = {
				0,
				1,
				1.4142135623730951D,
				2.23606797749979D,
				2.23606797749979D
			};

			for(int i = 0 ; i < vecteurs.length ; i++) {
				assertEquals(longeurs[i] , vecteurs[i].getLongueur() , 0F);
				
			}
	}
	
	@Test
	public void test_get(){
		Vecteur v = new Vecteur(new Position(51,53));
		Vecteur w = new Vecteur(new Position(-15,0));
		
		assertEquals((double)v.getLongueurX() , 51D,1E-14);
		assertEquals((double)v.getLongueurY() , 53D,1E-14);
		assertEquals((double)w.getLongueurX() , -15D,1E-14);
		assertEquals((double)w.getLongueurY() , 0D,1E-14);
	}
	
	@Test
	public void test_translation_point(){
		Vecteur v1 = new Vecteur(new Position(1,0));
		Vecteur v2 = new Vecteur(new Position(-1,0));
		Vecteur w1 = new Vecteur(new Position(0,1));
		Vecteur w2 = new Vecteur(new Position(0,-1));
		
		Position p = new Position(0,0);
		assertEquals(new Position(1,0),v1.translater(p));
		assertEquals(new Position(-1,0),v2.translater(p));
		assertEquals(new Position(0,1),w1.translater(p));
		assertEquals(new Position(0,-1),w2.translater(p));
	}
	
	@Test 
	public void test_ajoutVecteur_normal() {
		Vecteur[] vecteurs1 = {
			new Vecteur(new Position(1,1)),
			new Vecteur(new Position(1,1)),
			new Vecteur(new Position(1,0)),
			new Vecteur(new Position(0,1))
		};
		Vecteur[] vecteurs2 = {
			new Vecteur(new Position(1,1)),
			new Vecteur(new Position(0,0)),
			new Vecteur(new Position(1,1)),
			new Vecteur(new Position(1,1))
		};
		Position[] solutions = {
			new Position(2,2),
			new Position(1,1),
			new Position(2,1),
			new Position(1,2)
		};

		Vecteur resultat = new Vecteur(new Position(0,0));
		for(int i = 0 ; i < solutions.length ; i++) {
			resultat = new Vecteur(new Position(0,0));
			resultat.translater(vecteurs1[i]);
			resultat.translater(vecteurs2[i]);
			
			assertEquals(solutions[i] , resultat.getDestinationPoint());
		}
	}
}