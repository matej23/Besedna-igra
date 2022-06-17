package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import javax.swing.JPanel;

import logika.BarveCrke;
import logika.Igra;
import logika.Jezik;
import logika.Polje;
import logika.StanjeEnum;

@SuppressWarnings("serial")
class Crke extends JPanel{
	
	public int crkeVrstica;

	public Crke(Igra igra) {
		super();
		setPreferredSize(new Dimension(400, 400));
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		BarveCrke barveCrke = Okno.igra.barveCrke;
		Jezik jezik = Okno.igra.jezik;
		String[] crkeIzpis;
		
		Color crke1 = null;
		Color crke2 = null;
		
		if (jezik == Jezik.ANG) {
			crkeIzpis = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
			crkeVrstica = 4;
		}
		else {
			crkeIzpis = "ABCČDEFGHIJKLMNOPRSŠTUVZŽ".split("");
			crkeVrstica = 5;
		}
		   
		   int x = 100;
		   int y = 75;
		   
		   int m = crkeVrstica;
		   for (String crka : crkeIzpis) {
			   crke1 = barvaZaCrko(barveCrke, crka, 1);
			   crke2 = barvaZaCrko(barveCrke, crka, 2);
			   
			   if (m > 1) {
				   g.setColor(crke1);
				   g.fillRect(x, y, 23, 45);
				   g.setColor(crke2);
				   g.fillRect(x+23, y, 22, 45);
				   g.setColor(Color.BLACK); 
				   Rectangle rectangle = new Rectangle(x, y, 45, 45);
				   centerString(g, rectangle,crka.toUpperCase(),new Font("SansSerif Bold", Font.PLAIN, 30));
				   m-= 1;
				   x += 50;
			   }
			   else {
				   g.setColor(crke1);
				   g.fillRect(x, y, 23, 45);
				   g.setColor(crke2);
				   g.fillRect(x+23, y, 22, 45);
				   g.setColor(Color.BLACK);
				   Rectangle rectangle2 = new Rectangle(x, y, 45, 45);
				   centerString(g,rectangle2,crka.toUpperCase(),new Font("SansSerif Bold", Font.PLAIN, 30));
				   m = crkeVrstica;
				   x = 100;
				   y += 50;
			   }
		   }
		   
		   repaint();
	}
	
	public Color barvaZaCrko(BarveCrke barveCrke, String crka, int plosca) {
		   Color prazno = new Color(210, 210, 210); 
		   Color napacno = new Color(175, 170, 170);
		   Color pravilno = new Color(0,204,0);
		   Color delno_pravilno = new Color(250,250,50);
		   
		   HashMap<String, Polje> barve;
		   StanjeEnum stanje;
		   
		   if (plosca == 1) {
			   barve = barveCrke.barve1;
			   stanje = Igra.stanje.plosca1;
		   }
		   else {
			   barve = barveCrke.barve2;
			   stanje = Igra.stanje.plosca2;
		   }
		   if (stanje == StanjeEnum.ZMAGA) {
			   return new Color(150, 255, 150); 
		   }
		   else if (stanje == StanjeEnum.PORAZ) {
			   return new Color(255, 150, 150);
		   }
		   else {
			   if (barve.get(crka) == Polje.DELNOPRAVILNO) {
				   return delno_pravilno;
			   }
			   else if (barve.get(crka) == Polje.PRAVILNO) {
				   return pravilno;
			   }
			   else if (barve.get(crka) == Polje.NAPACNO) {
				   return napacno;
			   }
			   else {
				   return prazno;
			   }
		   }
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