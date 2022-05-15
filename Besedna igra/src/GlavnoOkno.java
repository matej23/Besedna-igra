import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import logika.Igra;
import logika.Vodja;
import logika.Cas;
import logika.Analiza;
import logika.Plosca;
import logika.Triple;
import logika.ColorTheme;
import logika.Jezik;
import logika.Polje;
import logika.Stanje;
import logika.StanjeEnum;

public class GlavnoOkno{

   public static void main(String[] args) {
	  Igra igra = new Igra(Jezik.SLO);
      Okno okno = new Okno(igra);
      okno.pack();
      okno.setVisible(true);
   }
}

//---------------------------------------------------

@SuppressWarnings("serial")
class Okno extends JFrame implements ActionListener{

   private Platno platno1;
   private Platno platno2;

   public Okno(Igra igra) {
      super();
      setTitle("Besedna igra");
      platno1 = new Platno(1);
      platno2 = new Platno(2);
      JTextField input = new JTextField(50); 
      input.addActionListener(this);
      add(input, BorderLayout.SOUTH);
      add(platno1, BorderLayout.WEST);
      add(platno2, BorderLayout.EAST);
      
   }

@Override
public void actionPerformed(ActionEvent e) {
	String text = e.getActionCommand();
	Igra.odigraj(text);
	repaint();
	}
	}

//---------------------------------------------------

@SuppressWarnings("serial")
class Platno extends JPanel {
	int st_plosce;
	private Polje[][] barve_polja;
	private String[][] plosca_vrednosti;
	
   public Platno(int st_plosce) {
      setPreferredSize(new Dimension(600, 600));
      this.st_plosce = st_plosce;

      if (st_plosce == 1) {
    	 barve_polja = Igra.plosca1_barve;
    	 plosca_vrednosti = Igra.plosca1_vrednosti;
      }
      else {
    	 barve_polja = Igra.plosca2_barve;
    	 plosca_vrednosti = Igra.plosca2_vrednosti;
      }
   }
   
   public void paint(Graphics g) {
       super.paint(g);
       draw(g);
   }

   public void draw(Graphics g){
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
		   x = 70;
		   y+= 70;
	   }
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


