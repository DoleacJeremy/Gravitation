package com.JD.math.geometrie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DroiteTest {

	@Test
	public void testIntersection() {
		double constX = 14;
		double constY = 14;
		
		//I
		Droite constanteYI = new Droite(new Position(1D,constY), new Position(2D,constY));
		Droite constanteYII = new Droite(new Position(1D,constY+1), new Position(2D,constY+1));
		//II
		Droite constanteXI = new Droite(new Position(constX, 1D), new Position(constX, 2D));
		Droite constanteXII = new Droite(new Position(constX+1, 1D), new Position(constX+1, 2D));
		//III
		Droite droiteNormaleI = new Droite(new Position(0,0), new Position(1,1));
		//IV
		Droite droiteNormaleII = new Droite(new Position(-1,-1), new Position(-8,7));
		Droite droiteNormaleIII = new Droite(new Position(0,-1), new Position(-5,9));
		
		//I
		assertNull(Droite.intersection(constanteYI, constanteYII));
		assertNull(Droite.intersection(constanteYI, constanteYI));
		//II
		assertNull(Droite.intersection(constanteXI, constanteXII));
		assertEquals(Droite.intersection(constanteXI, constanteYI),new Position(constX,constY));
		assertEquals(Droite.intersection(constanteXI, constanteYII),new Position(constX,constY+1));
		assertEquals(Droite.intersection(constanteXII, constanteYI),new Position(constX+1,constY));
		assertEquals(Droite.intersection(constanteXII, constanteYII),new Position(constX+1,constY+1));
		//III
		assertEquals(Droite.intersection(constanteYI, droiteNormaleI),new Position(14D,constY));
		assertEquals(Droite.intersection(constanteXI, droiteNormaleI),new Position(constX,14D));
		assertEquals(Droite.intersection(droiteNormaleI, constanteYI),Droite.intersection(constanteYI, droiteNormaleI));
		assertEquals(Droite.intersection(droiteNormaleI, constanteXI),Droite.intersection(constanteXI, droiteNormaleI));
		//IV
		assertEquals(Droite.intersection(droiteNormaleI, droiteNormaleII),new Position(-1,-1));
		assertEquals(Droite.intersection(droiteNormaleI, droiteNormaleIII),new Position(-1/3D,-1/3D));
		assertEquals(Droite.intersection(droiteNormaleII, droiteNormaleIII),new Position(4/3D,-11/3D));
		
	}

	@Test
	public void testApartientDroiteNormales() {
		Droite d = new Droite(new Position(1D, 1D), new Position(2D, 2D));

		Position[] positionsValides = {
			new Position(4D,4D),
			new Position(8D,8D)	,
			new Position(-0.00000000078D,-0.00000000078D)			
		};
		Position[] positionsInvalides = {
			new Position(4D,4D-0.00000000078D),
			new Position(8D,8D-1)			
		};

		for(Position p : positionsValides)
			assertTrue(d.apartientDroite(p));
		for(Position p : positionsInvalides)
			assertFalse(d.apartientDroite(p));
	}
	@Test
	public void testApartientDroiteConstanteX() {
		double x = 14D;
		Droite d = new Droite(new Position(x,1D), new Position(x,2D));

		Position[] positionsValides = {
			new Position(x,4D),
			new Position(x,8D)			
		};
		Position[] positionsInvalides = {
			new Position(x-0.00000000078D,4D),
			new Position(x-1,8D)			
		};

		for(Position p : positionsValides)
			assertTrue(d.apartientDroite(p));
		for(Position p : positionsInvalides)
			assertFalse(d.apartientDroite(p));
	}
	@Test
	public void testApartientDroiteConstanteY() {
		double y = 14D;
		Droite d = new Droite(new Position(1D, y), new Position(2D, y));

		Position[] positionsValides = {
			new Position(4D,y),
			new Position(8D,y)			
		};
		Position[] positionsInvalides = {
			new Position(4D,y-0.00000000078D),
			new Position(8D,y-1)			
		};

		for(Position p : positionsValides)
			assertTrue(d.apartientDroite(p));
		for(Position p : positionsInvalides)
			assertFalse(d.apartientDroite(p));
	}
	@Test
	public void testPerpendiculairePointI() {
		Position[] pointPerpendiculaire = {
			new Position(4D,7D),
			new Position(8D,9D)	,
			new Position(2,7)
		};
		
		//constante X
		double constX = 14;
		Droite dConstX = new Droite(new Position(constX, 1D), new Position(constX, 2D));
		for(Position p : pointPerpendiculaire) {
			Droite perpendiculairedConstX = dConstX.perpendiculaire(p);
			assertTrue(perpendiculairedConstX.isConstantY());
			assertTrue(perpendiculairedConstX.apartientDroite(p));
		}
		
		//constante Y
		double constY = 14;
		Droite dConstY = new Droite(new Position(1D, constY), new Position(2D, constY));
		for(Position p : pointPerpendiculaire) {
			Droite perpendiculairedConstY = dConstY.perpendiculaire(p);
			assertTrue(perpendiculairedConstY.isConstantX());
			assertTrue(perpendiculairedConstY.apartientDroite(p));
		}

		//normale
		Droite[] droites = {
				new Droite(new Position(0,0), new Position(1,1)),
				new Droite(new Position(0,0), new Position(4,1)),
				new Droite(new Position(-1,-1), new Position(-8,7)),
				new Droite(new Position(3, 4) , new Position(4, 1))
		};
		Position[] positionsPrep = {
			new Position(5,5),
			new Position(4,1),
			new Position(6,-9),
			new Position(2,7)
		};
		Droite[] solutions = {
				new Droite(new Position(5,5), new Position(4,6)),
				new Droite(new Position(3,5), new Position(4,1)),
				new Droite(new Position(-2,-16), new Position(6,-9)),
				new Droite(new Position(2,7), new Position(-1,6))
		};
		for(int i = 0 ; i < droites.length ; i++) {
			Droite perpendiculairedD = droites[i].perpendiculaire(positionsPrep[i]);
			if(!perpendiculairedD.equals(solutions[i]))
				droites[i].perpendiculaire(positionsPrep[i]);
			assertEquals(perpendiculairedD, solutions[i]);
		}
		


		Droite d1 = new Droite(new Position(3, 4) , new Position(4, 1));
		Position point = new Position(2,7);
		Droite perpendiculairePresumer = d1.perpendiculaire(point);
		Droite perpendiculaire = new Droite(new Position(2,7), new Position(-1,6));
		assertEquals(perpendiculairePresumer,perpendiculaire);
	}
	@Test
	public void testGetPositionDistance() {
		Position[] point = {
				new Position(4D,7D),
				new Position(8D,9D)			
			};
		double[] distances = {
			0,
			14,
			-8
		};
		double constX = 14;
		double constY = 14;
		double delta = 1E-14;

		//normale
		Droite[] droites = {
				new Droite(new Position(0,0), new Position(1,1)),
				new Droite(new Position(0,0), new Position(4,1)),
				new Droite(new Position(-1,-1), new Position(-8,7)),
				new Droite(new Position(0,-1), new Position(-5,9)),
				new Droite(new Position(1D,constY), new Position(2D,constY)),
				new Droite(new Position(constX, 1D), new Position(constX, 2D))
		};
		
		//teste plusieurs droites
		for(Droite d : droites) {
			//teste pour plusieurs distances
			for(double distance : distances) {
				//teste pout plusieurs points 
				for(Position p : point) {
					Position plusproche = d.getPlusProchePoint(p);
					
					if(plusproche == null)
						d.getPlusProchePoint(p);
					
					//premiéres solutions avec distance
					Position[] solutions = d.getPositionDistance(p, distance);
					for(Position solution : solutions) {
						if(!d.apartientDroite(solution)) {
							d.apartientDroite(solution);
						}
						assertTrue(d.apartientDroite(solution));
						double distanceCalculated = Position.getDistance(plusproche, solution);
						assertEquals(distanceCalculated,Math.abs(distance),delta);
					}
					//secondes solutions avec -distance
					Position[] solutionsNegatif = d.getPositionDistance(p, -distance);
					for(Position solution : solutionsNegatif) {
						assertTrue(d.apartientDroite(d.getPlusProchePoint(solution)));
						double distanceCalculated = Position.getDistance(plusproche, solution);
						assertEquals(distanceCalculated,Math.abs(distance),delta);
					}
					assertEquals(solutions[0],solutionsNegatif[0]);
					assertEquals(solutions[1],solutionsNegatif[1]);
				}
			}
		}
	}
	@Test
	public void testEquals() {
		double constX = 14;
		double constY = 14;
		
		//I
		Droite constanteYI = new Droite(new Position(1D,constY), new Position(2D,constY));
		Droite constanteYII = new Droite(new Position(1D,constY+1), new Position(2D,constY+1));
		Droite constanteYIII = new Droite(new Position(3D,constY+1), new Position(814_000_069D,constY+1));
		//II
		Droite constanteXI = new Droite(new Position(constX, 1D), new Position(constX, 2D));
		Droite constanteXII = new Droite(new Position(constX+1, 1D), new Position(constX+1, 2D));
		Droite constanteXIII = new Droite(new Position(constX+1, 3D), new Position(constX+1, 814_000_069D));
		//III
		Droite droiteNormaleI = new Droite(new Position(0,0), new Position(1,1));
		//IV
		Droite droiteNormaleII = new Droite(new Position(-1,-1), new Position(-2,-2));
		Droite droiteNormaleIII = new Droite(new Position(0,-1), new Position(-5,9));
		//V
		Droite droiteNull = null;
		Object objetPasDroite = new Object();
		Double doublePasDroite = 14D;

		//I
		assertNotEquals(constanteYI,constanteYII);
		assertNotEquals(constanteYI,constanteYIII);
		assertEquals(constanteYII,constanteYIII);
		//II
		assertNotEquals(constanteXI,constanteXII);
		assertNotEquals(constanteXI,constanteXIII);
		assertNotEquals(constanteYI,constanteXI);
		assertEquals(constanteXII,constanteXIII);
		//III
		assertEquals(droiteNormaleI,droiteNormaleII);
		assertNotEquals(constanteYI,droiteNormaleI);
		assertNotEquals(constanteXI,droiteNormaleI);
		assertNotEquals(droiteNormaleI,droiteNormaleIII);
		assertNotEquals(droiteNormaleIII,droiteNormaleIII.perpendiculaire());
		//V
		assertNotEquals(droiteNormaleIII,droiteNull);
		assertNotEquals(droiteNull,droiteNormaleIII);
		assertNotEquals(objetPasDroite,droiteNormaleIII);
		assertNotEquals(doublePasDroite,droiteNormaleIII);
		assertEquals(droiteNormaleIII,droiteNormaleIII);
	}
	@Test
	public void testConstructeurDroite() {
		Droite d1 = new Droite(1,1);
		Droite d3 = new Droite(1,2);
		Droite d2 = new Droite(new Position(0,1),new Position(1,2));
		
		assertEquals(d1,d2);
		assertNotEquals(d1,d3);
	}
	@Test
	public void testgetPlusProchePoint() {
		Droite d1 = new Droite(1,0);

		Position[] points = {
			new Position(2,0),
			new Position(1,1),
			new Position(2,1)
		};
		Position[] attendu = {
			new Position(1,1),
			new Position(1,1),
			new Position(1.5D,1.5D)
		};
		for(int i = 0 ; i < points.length ; i++) {
			Position p = d1.getPlusProchePoint(points[i]);
			assertEquals(p,attendu[i]);
		}
	}
	@Test
	public void testPerpendiculaire() {
		Droite droite = new Droite(1,0);
		Droite perpendiculairePresumer = droite.perpendiculaire();
		Droite perpendiculaire = new Droite(-1,0);
		assertEquals(perpendiculairePresumer,perpendiculaire);

		droite = new Droite(3,4);
		perpendiculairePresumer = droite.perpendiculaire();
		perpendiculaire = new Droite((-(1.0D/3)),4);
		assertEquals(perpendiculairePresumer,perpendiculaire);

		
		droite = new Droite(1,0);
		Position point = new Position(2,0);
		perpendiculairePresumer = droite.perpendiculaire(point);
		perpendiculaire = new Droite(-1,2);
		assertEquals(perpendiculairePresumer,perpendiculaire);

		droite = new Droite(new Position(200, 400) , new Position(400, 400));
		point = new Position(200,400);
		perpendiculairePresumer = droite.perpendiculaire(point);
		perpendiculaire = new Droite(new Position(200,400), new Position(200, 600));
		assertEquals(perpendiculairePresumer,perpendiculaire);
	}
	@Test
	public void testGetA() {
		Droite[] droites = {
				new Droite(new Position(0, 0),new Position(1, 1)),
				new Droite(new Position(0, 0),new Position(1, -1)),
				new Droite(new Position(0, 1),new Position(4, 1))
		};
		double[] results = {
				1D,
				-1D,
				0D
		};
		Double a,result;
		for(int i = 0 ; i < droites.length ; i++) {
			a = droites[i].getA();
			result = results[i];
			assertTrue(a.equals(result));
		}
	}

}