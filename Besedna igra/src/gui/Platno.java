package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
//import java.util.LinkedList;

import javax.swing.JPanel;

import logika.Polje;
import logika.StanjeEnum;

@SuppressWarnings("serial")
class Platno extends JPanel {
	int st_plosce;
	private Color defaultColor;
//	private StanjeEnum stanje;
//	private Polje[][] barve_polja;
//	private String[][] plosca_vrednosti;
//	LinkedList<Integer> steviloBesed;
	
   public Platno(int st_plosce) {
	  super();
      setPreferredSize(new Dimension(550, 550));
      this.st_plosce = st_plosce;
      this.defaultColor = getBackground();

//      if (st_plosce == 1) {
//    	 stanje = Okno.igra.stanje.plosca1;
//    	 barve_polja = Okno.igra.plosca1_barve;
//    	 plosca_vrednosti = Okno.igra.plosca1_vrednosti;
//    	 steviloBesed = Okno.igra.steviloBesed1;
//      }
//      else {
//    	 stanje = Okno.igra.stanje.plosca2;
//    	 barve_polja = Okno.igra.plosca2_barve;
//    	 plosca_vrednosti = Okno.igra.plosca2_vrednosti;
//    	 steviloBesed = Okno.igra.steviloBesed2;
//      }
   }
   
//   public void paint(Graphics g) {
//       super.paint(g);
//       draw(g);
//   }

   public void paintComponent(Graphics g){
	   super.paintComponent(g);
	   StanjeEnum stanje;
	   Polje[][] barve_polja;
	   String[][] plosca_vrednosti;
//	   LinkedList<Integer> steviloBesed;
	   
	   if (st_plosce == 1) {
		   stanje = Okno.igra.stanje.plosca1;
		   barve_polja = Okno.igra.plosca1_barve;
		   plosca_vrednosti = Okno.igra.plosca1_vrednosti;
//		   steviloBesed = Okno.igra.steviloBesed1;
	      }
	  else {
		  stanje = Okno.igra.stanje.plosca2;
		  barve_polja = Okno.igra.plosca2_barve;
		  plosca_vrednosti = Okno.igra.plosca2_vrednosti;
//		  steviloBesed = Okno.igra.steviloBesed2;
	      }
	   
	   if (stanje == StanjeEnum.PORAZ) {
		   setBackground(new Color(255, 150, 150));
	   }
	   else if(stanje == StanjeEnum.ZMAGA) {
		   setBackground(new Color(150, 255, 150));
	   }
	   else setBackground(defaultColor);
	   
	   Color prazno = new Color(210, 210, 210); 
	   Color napacno = new Color(175, 170, 170);
	   Color pravilno = new Color(0,204,0);
	   Color delno_pravilno = new Color(250,250,50);
	   int x = 70;
	   int y = 70;
	   for (int i = 0; i < barve_polja.length; i++) {
		   for (int j = 0; j < barve_polja[0].length; j++) {
			   if (barve_polja[i][j] == Polje.PRAZNO) {
				   g.setColor(prazno);
			   }
			   else if(barve_polja[i][j] == Polje.NAPACNO) {
				   g.setColor(napacno);
			   }
			   else if(barve_polja[i][j] == Polje.PRAVILNO) {
				   g.setColor(pravilno);
			   }
			   else {
				   g.setColor(delno_pravilno);
			   }
			   
			   g.fillRoundRect(x, y, 65, 65 , 10, 10);
			   g.setColor(Color.BLACK); 
			   Rectangle rectangle = new Rectangle(x,y,65,65);
			   centerString(g,rectangle,plosca_vrednosti[i][j].toUpperCase(),new Font("SansSerif Bold", Font.PLAIN, 50));
			   x+= 70;
		   }
//		   if (barve_polja[i][0] != Polje.PRAZNO) {
//			   String moznostiStr = Integer.toString(steviloBesed.get(i));
//			   g.drawString(moznostiStr, x + 10, y + 50);
//			   
//		   }
		   x = 70;
		   y+= 70;
	   }
	   repaint();
   }
   
   public void centerString(Graphics g, Rectangle r, String s, 
	        Font font) {
	    FontRenderContext frc = 
	            new FontRenderContext(null, true, true);

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