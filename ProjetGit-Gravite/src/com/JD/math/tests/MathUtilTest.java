package com.JD.math.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.JD.math.general.FonctionsTest;
import com.JD.math.geometrie.CercleTest;
import com.JD.math.geometrie.DroiteTest;
import com.JD.math.geometrie.FunctionGeometrieTest;
import com.JD.math.geometrie.PositionTest;
import com.JD.math.geometrie.VecteurTest;

@RunWith(Suite.class)
@SuiteClasses({ 
	FonctionsTest.class,
	
	CercleTest.class,
	DroiteTest.class,
	FunctionGeometrieTest.class,
	PositionTest.class,
	VecteurTest.class,
	})

public class MathUtilTest {}