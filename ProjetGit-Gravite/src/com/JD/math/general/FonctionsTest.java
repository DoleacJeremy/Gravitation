package com.JD.math.general;

import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;

public class FonctionsTest extends TestCase{

	@Test
	public void test_arrondi_dessous() {
		float value = 45.49999F;
		int resultat = Functions.round(value);
		assertEquals(resultat , 45);
		
		value = -2.59999F;
		resultat = Functions.round(value);
		assertEquals(resultat,-3);
	}
	
	@Test
	public void test_arrondi_dessus() {
		float value = 45.59999F;
		int resultat = Functions.round(value);
		assertEquals(resultat , 46);
	}
	
	@Test
	public void test_arrondi_5() {
		float value = 45.5F;
		int resultat = Functions.round(value);
		assertEquals(resultat , 46);
	}
	
	@Test
	public void test_arrondi_pas() {
		float value = 45.0F;
		int resultat = Functions.round(value);
		assertEquals(resultat , 45);
	}
	
	@Test
	public void test_estInteger(){
		double[] valideInt = {
			-1,
			2,
			9000,
			54654765,
			8.0D
		};
		double[] invalideInt = {
			1.00000000000001D,
			-3.141592D,
			9000.1,
			1.0E-10
		};

		for(double valeurDouble : valideInt) {
			assertTrue(Functions.estInteger(valeurDouble));
		}
		for(double valeurDouble : invalideInt) {
			assertFalse(Functions.estInteger(valeurDouble));
		}
	}
	
	@Test
	public void test_calculDelta() {
		double[][] equations = {
			{128,64,32},
			{-1,2,3},
			{1,-2,1},
			{7,5,-12}
		};
		
		double[][] solutions = {
			{},
			{3,-1},
			{1},
			{-12D/7D,1}
		};
		
		for(int i = 0 ; i < equations.length ; i++) {
			double[] equation = equations[i];
			double[] solution = solutions[i];
			List<Double> solutionTrouve = Functions.calculDelta(equation[0],equation[1],equation[2]);
			
			if(solutionTrouve.size() != solution.length)
				fail();
			
			for(double s : solution) {
				assertTrue(solutionTrouve.contains(s));
			}
		}
	}
}