package com.JD.math.geometrie;

import java.awt.Color;
import java.awt.Graphics;

public abstract class MathGraphique {
	private Color color = Color.PINK;
	
	public void setColor(Color color) {
		this.color = color;
	}
	public Color getColor() {
		return(this.color);
	}
	
	public abstract void drawMathObject(int limiteX , int limiteY , Graphics g , double zoom);
}