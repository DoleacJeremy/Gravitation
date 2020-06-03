package com.JD.math.geometrie;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CercleTest {


	@Test
	public void test_appartienCercle() {
		Cercle cercle = new Cercle(new Position(0,0),new Position(10,0));
		
		Position[] pointCercle = {
			new Position(-9.797958971132712D,2D),
			new Position(10,0),
			new Position(-10,0),
			new Position(0,10),
			new Position(0,-10),
			new Position(6,-8),
			new Position(6,8),
			new Position(9.797958971132712D,2D)
		};
		Position[] pointPasCercle = {
			new Position(11,10),
			new Position(-1,-1),
			new Position(0,0)
		};
		
		for(Position position : pointPasCercle) {
			assertFalse(cercle.appartienPerimetreCercle(position));
		}
		for(Position position : pointCercle) {
			assertTrue(cercle.appartienPerimetreCercle(position));
		}
	}
}