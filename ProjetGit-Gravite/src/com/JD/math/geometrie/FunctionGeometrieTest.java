package com.JD.math.geometrie;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class FunctionGeometrieTest {

	
	@Test
	void testIntersectionDroiteCercle() {
		Position centre = new Position(0,0);
		Position rayon = new Position(5,0);
		Cercle cercle = new Cercle(centre, rayon);
		
		Droite[] droites = {
			new Droite(centre, new Position(5,0)),
			new Droite(centre, new Position(-5,0)),
			new Droite(centre, new Position(0,5)),
			new Droite(new Position(-1,-1), new Position(1,1)),
			new Droite(new Position(-1.5D,-1D), new Position(1,1)),
			new Droite(new Position(0,0), new Position(0.1D,10D))
		};
		Position[][] solutionsAttendue = {
			{new Position(-5, 0),new Position(5, 0)},
			{new Position(-5, 0),new Position(5, 0)},
			{new Position(0, -5),new Position(0, 5)},
			{new Position(-3.5355339059327378, -3.5355339059327378),new Position(3.5355339059327378, 3.5355339059327378)},
			{new Position(-4.0 , -3.0),new Position(3.8048780487804876 , 3.2439024390243905)},
			{new Position(-0.049997500187484376 , -4.999750018748437),new Position(0.049997500187484376 , 4.999750018748437)}
		};
		
		for (int i = 0 ; i < droites.length ; i++) {
			ArrayList<Position> solutionsObtenue = (ArrayList<Position>) FunctionGeometrie.getIntersection(droites[i], cercle);
			

			assertTrue(solutionsAttendue[i].length == solutionsObtenue.size());
			for(Position attandue : solutionsAttendue[i]) {
				assertTrue(solutionsObtenue.contains(attandue));
			}
		}
	}
	@Test
	void testIntersectionDroiteCercleII() {
		Position centre = new Position(400.0 , 400.0);
		Position rayon = new Position(360.0 , 400.0);
		Cercle cercle = new Cercle(centre, rayon);
		
		Droite[] droites = {
			new Droite(new Position(360.00000625 , 400.0), new Position(360.00000625 , 360.00000625))
		};
		Position[][] solutionsAttendue = {
			{new Position(360.00000625 , 399.9776393210769),new Position(360.00000625 , 400.0223606789231)}
		};
		
		for (int i = 0 ; i < droites.length ; i++) {
			ArrayList<Position> solutionsObtenue = (ArrayList<Position>) FunctionGeometrie.getIntersection(droites[i], cercle);
			

			assertTrue(solutionsAttendue[i].length == solutionsObtenue.size());
			for(Position attandue : solutionsAttendue[i]) {
				assertTrue(solutionsObtenue.contains(attandue));
			}
		}
	}

}
