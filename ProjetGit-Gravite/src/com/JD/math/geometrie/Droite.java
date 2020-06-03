package com.JD.math.geometrie;

import java.awt.Graphics;

import com.JD.math.general.Functions;

public class Droite extends MathGraphique{
	Position p1;
	Position p2;


	//creation d'une droite a partir d'un couple classique de a et b
	public Droite(double a, double b) {
		int limitePrecision = 1000;
		double x = 0;
		double y = a*x+b;
		
		//premier point
		while(!Functions.estInteger(y) && x < limitePrecision) {
			x++;
			y = a*x+b;
		}
		this.p1 = new Position(x, y);
		
		//second point
		x++;
		y = a*x+b;
		while(!Functions.estInteger(y) && x < 2*limitePrecision) {
			x++;
			y = a*x+b;
		}
		this.p2 = new Position(x, y);
	}
	//creation d'une droite a partir de deux points
	public Droite(Position p1, Position p2) {
		this.p1 = new Position(p1.getX(),p1.getY());
		this.p2 = new Position(p2.getX(),p2.getY());
	}

		
		
	// getters
	public Position getP1() {
		return(this.p1);
	}
	public Position getP2() {
		return(this.p2);
	}
	public double getB() {
		double b = this.p1.getY()-this.getA()*this.p1.getX();
		return(b);
	}
	public double getA() {
		double x = this.p2.getX() - this.p1.getX();
		double y = this.p2.getY() - this.p1.getY();
		double a = 0;
		if (x != 0) {
			a = y / x;
		}
		return(a);
	}
	
	// permet de trouver le plus proche point de p2 qui appartient a la droite
	public Position getPlusProchePoint(Position point) {
		//si c'est une constante
		if(this.isConstantX())
			return(new Position(this.p1.getX(),point.getY()));
		if(this.isConstantY())
			return(new Position(point.getX(),this.p1.getY()));
		
		//si ce n'est pas une constante
		double yp = this.getA()*point.getX()+this.getB();
		double yfinal = (point.getY()+yp)/2;
		double xfinal = (yfinal - this.getB())/this.getA();
		
		Position retour = new Position(xfinal,yfinal);
		return (retour);
	}


	// permet de dire si un point appartient a la droite
	public boolean apartientDroite(Position p) {
		if(this.isConstantX()) 
			return(p.getX().equals(this.p2.getX()));
		if(this.isConstantY())
			return(p.getY().equals(this.p2.getY()));
		
		double res = this.getA() * p.getX() + this.getB();
		boolean retour = Math.abs((p.getY()-res)) < 1.0E-14;
		return (retour);
	}
	

	// permet de trouver la perpendiculaire d'une droite qui passe par un point donné
	public Droite perpendiculaire(Position p) {
		//dans le cas des constant droite qui foutent la merde genre tout droit en x ou y
		Position plusproche = p;
		Droite retour = null;
		if(this.isConstantX()) {
			//si c'est une constante en X la perpendiculaire est une constante en Y
			retour = new Droite(new Position(this.p2.getY(), p.getY()), new Position(this.p1.getY(),p.getY()));
			return(retour);
		}
		if(this.isConstantY()) {
			//si c'est une constante en Y la perpendiculaire est une constante en X
			retour = new Droite(new Position(p.getX(),this.p2.getX()), new Position(p.getX(),this.p1.getX()));
			return(retour);
		}
		//cas normal
		if(!this.apartientDroite(p))
			plusproche = this.getPlusProchePoint(p);
		
		double perpendiculaireA = (-1D)/this.getA();
		double perpendiculaireB = plusproche.getY()-perpendiculaireA*plusproche.getX();
		
		retour = new Droite(perpendiculaireA,perpendiculaireB);
		return (retour);
	}
	// permet de trouver la perpendiculaire d'une droite
	public Droite perpendiculaire() {
		double a = -(1 / this.getA());
		double b = this.getB();

		Droite retour = new Droite(a, b);

		return (retour);
	}
	// permet d'obtenir la position du point d'intersection des deux droites passées en paramétre
	public static Position intersection(Droite droite1, Droite droite2) {
		//deux constante en x et y qui se touchent 
		if(droite1.isConstantX() && droite2.isConstantY())
			return(new Position(droite1.getP1().getX(),droite2.getP1().getY()));
		if(droite1.isConstantY() && droite2.isConstantX())
			return(new Position(droite2.getP1().getX(),droite1.getP1().getY()));
		//deux paralélles
		if (droite1.getA() == droite2.getA())
			return (null);
		
		//une seule constante
		if(droite1.isConstantY()) {
			return(intersectionY(droite1,droite2));
		}
		if(droite2.isConstantY()) {
			return(intersectionY(droite2,droite1));
		}
		if(droite1.isConstantX()) {
			return(intersectionX(droite1,droite2));
		}
		if(droite2.isConstantX()) {
			return(intersectionX(droite2,droite1));
		}

		// cas normal
		double x = (droite2.getB() - droite1.getB()) / (droite1.getA() - droite2.getA());
		double y = droite1.getA() * x + droite1.getB();

		return (new Position(x, y));
	}
	// donne le point d'intersection d'une constante en Y avec une droite normale
	private static Position intersectionY(Droite constanteY, Droite droite) {
		double y = constanteY.getP1().getY();
		double x = (y - droite.getB()) / droite.getA();
		return (new Position(x, y));
	}
	// donne le point d'intersection d'une constante en X avec une droite normale
	private static Position intersectionX(Droite constanteX, Droite droite) {
		double x = constanteX.getP1().getX();
		double y = droite.getA()*x+droite.getB();
		return (new Position(x, y));
	}
	
	// donne les points à une distance "distance" d'un point "point" donné en paramétre
	public Position[] getPositionDistance(Position point,double distance) {
		Position[] positions = new Position[2];
		double a = this.getA();
		double b = this.getB();
		double distanceAbsolue = Math.abs(distance);
		Position pointCentral = point;
		if(!this.apartientDroite(pointCentral)) {
			pointCentral = this.getPlusProchePoint(point);
		}

		// le cas des constantes en X et Y
		if(this.isConstantX()) {
			positions[0] = new Position(this.p1.getX(),pointCentral.getY()-distanceAbsolue);
			positions[1] = new Position(this.p1.getX(),pointCentral.getY()+distanceAbsolue);
			return(positions);
		}
		if(this.isConstantY()) {
			positions[0] = new Position(pointCentral.getX()-distanceAbsolue,this.p1.getY());
			positions[1] = new Position(pointCentral.getX()+distanceAbsolue,this.p1.getY());
			return(positions);
		}
		
		// cas normal
		int x = pointCentral.getX().intValue();
		Position point1 = new Position(x+1D, a*(x+1)+b);
		Position point2 = new Position(x-1D, a*(x-1)+b);

		Vecteur vecteur = new Vecteur(pointCentral, point1);
		vecteur.setLongueur(distanceAbsolue);
		point1 = new Position(pointCentral.getX()+vecteur.getLongueurX(), pointCentral.getY()+vecteur.getLongueurY());
		vecteur = new Vecteur(pointCentral, point2);
		vecteur.setLongueur(distanceAbsolue);
		point2 = new Position(pointCentral.getX()+vecteur.getLongueurX(), pointCentral.getY()+vecteur.getLongueurY());

		positions[0] = point1;
		positions[1] = point2;
		return(positions);
	}
	

	// permet de dire si la droite est une constante en X
	public boolean isConstantX() {
		return(this.p1.getX().equals(this.p2.getX()));
	}
	// permet de dire si la droite est une constante en Y
	public boolean isConstantY() {
		return(this.p1.getY().equals(this.p2.getY()));
	}
	


	
	
	
	
	
	
	
	
	
	

	@Override
	public void drawMathObject(int limiteX, int limiteY, Graphics g , double zoom) {
		double a = this.getA();
		double b = this.getB();
		Double x1,y1,x2,y2;

		if(this.isConstantX()) {
			x1 = this.p1.getX()*zoom;
			x2 = x1;
			y1 = 0D;
			y2 = limiteY*zoom+0D;
		}else {
			x1 = 0D;
			y1 = b*zoom;
			x2 = limiteX*zoom+0D;
			y2 = (a*limiteX+b)*zoom;
		}
		
		g.setColor(this.getColor());
		g.drawLine(x1.intValue(),y1.intValue(),x2.intValue(),y2.intValue());
	}
	@Override
	public String toString() {
		return "Droite [p1=" + p1 + ", p2=" + p2 + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((p1 == null) ? 0 : p1.hashCode());
		result = prime * result + ((p2 == null) ? 0 : p2.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Droite other = (Droite) obj;

		//cas particuliers
		if(this.isConstantX() && other.isConstantX())
			return(this.getP1().getX().equals(other.getP1().getX()));
		if(this.isConstantY() && other.isConstantY())
			return(this.getP1().getY().equals(other.getP1().getY()));
		if(this.isConstantY() && other.isConstantX() || this.isConstantX() && other.isConstantY())
			return(false);
		//cas normal
		return(this.getA() == other.getA() && this.getB() == other.getB());
	}
}