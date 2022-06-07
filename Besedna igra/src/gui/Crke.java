package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import logika.BarveCrke;
import logika.Igra;
import logika.Jezik;
import logika.Polje;

@SuppressWarnings("serial")
class Crke extends JPanel{
	
//	String geslo1;
//	String geslo2;
//	Jezik jezik;
//	String[] crkeIzpis;
//	int crkeVrstica;
	
//	BarveCrke barveCrke;
	
	public Crke(Igra igra) {
		setPreferredSize(new Dimension(400, 400));
//		barveCrke = igra.barveCrke;
//		geslo1 = igra.beseda1;
//		geslo2 = igra.beseda2;
		
//		if (igra.jezik == Jezik.ANG) {
//			crkeIzpis = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
//			crkeVrstica = 4;
//		}
//		else {
//			crkeIzpis = "ABCČDEFGHIJKLMNOPRSŠTUVZŽ".split("");
//			crkeVrstica = 5;
//		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
	    draw(g);
	}
	
	public void draw(Graphics g){
		BarveCrke barveCrke = Okno.igra.barveCrke;
		String geslo1 = Okno.igra.beseda1;
		String geslo2 = Okno.igra.beseda2;
		Jezik jezik = Okno.igra.jezik;
		String[] crkeIzpis;
		int crkeVrstica;
		if (jezik == Jezik.ANG) {
			crkeIzpis = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
			crkeVrstica = 4;
		}
		else {
			crkeIzpis = "ABCČDEFGHIJKLMNOPRSŠTUVZŽ".split("");
			crkeVrstica = 5;
		}
		
		   Color prazno = new Color(210, 210, 210); 
		   Color napacno = new Color(175, 170, 170);
		   Color pravilno = new Color(0,204,0);
		   Color delno_pravilno = new Color(250,250,50);
		   
		   int x = 100;
		   int y = 75;
		   
		   int m = crkeVrstica;
		   for (String crka : crkeIzpis) {
			   if (barveCrke.barve1.get(crka) == Polje.DELNOPRAVILNO) {
				   g.setColor(delno_pravilno);
			   }
			   else if (barveCrke.barve1.get(crka) == Polje.PRAVILNO) {
				   g.setColor(pravilno);
			   }
			   else if (barveCrke.barve1.get(crka) == Polje.NAPACNO) {
				   g.setColor(napacno);
			   }
			   else {
				   g.setColor(prazno);
			   }
			   
			   if (m > 1) {
				   g.fillRect(x, y, 45, 45);
				   g.setColor(Color.BLACK); 
				   Rectangle rectangle = new Rectangle(x, y, 45, 45);
				   centerString(g, rectangle,crka.toUpperCase(),new Font("SansSerif Bold", Font.PLAIN, 30));
				   m-= 1;
				   x += 50;
			   }
			   else {
				   g.fillRect(x, y, 45, 45);
				   g.setColor(Color.BLACK);
				   Rectangle rectangle2 = new Rectangle(x, y, 45, 45);
				   centerString(g,rectangle2,crka.toUpperCase(),new Font("SansSerif Bold", Font.PLAIN, 30));
				   m = crkeVrstica;
				   x = 100;
				   y += 50;
			   }
		   }
		   Rectangle rectangle3 = new Rectangle(x, y, 100,50);
		   centerString(g,rectangle3,geslo1.toUpperCase(),new Font("SansSerif Bold", Font.PLAIN, 30));
		   Rectangle rectangle4 = new Rectangle(x, y + 60, 100, 50);
		   centerString(g,rectangle4,geslo2.toUpperCase(),new Font("SansSerif Bold", Font.PLAIN, 30));
	}
	
	public void centerString(Graphics g, Rectangle r, String s, Font font) {
		FontRenderContext frc = new FontRenderContext(null, true, true);
		Rectangle2D r2D = font.getStringBounds(s, frc);
	    int rWidth = (int) Math.round(r2D.getWidth());
	    int rHeight = (int) Math.round(r2D.getHeight());		   
	    int rX = (int) Math.round(r2D.getX());
	    int rY = (int) Math.round(r2D.getY());

	    int a = (r.width / 2) - (rWidth / 2) - rX;
	    int b = (r.height / 2) - (rHeight / 2) - rY;

	    g.setFont(font);
	    g.drawString(s, r.x + a, r.y + b);
	}
	
	
}