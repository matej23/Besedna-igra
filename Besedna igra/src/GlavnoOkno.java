import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logika.BarveCrke;
import logika.Igra;
import logika.Jezik;
import logika.Polje;
import logika.Stanje;
import logika.StanjeEnum;


public class GlavnoOkno{

   public static void main(String[] args) {
	  Igra igra = new Igra(Jezik.ANG);
      Okno okno = new Okno(igra);
      okno.pack();
      okno.setVisible(true);
   }
}

//---------------------------------------------------

@SuppressWarnings("serial")
class Okno extends JFrame implements ActionListener, KeyListener{

   private Platno platno1;
   private Platno platno2;

   public Okno(Igra igra) {
      super();
      setTitle("Besedna igra");
      
      platno1 = new Platno(1);
      platno2 = new Platno(2);     
      
      Crke crke = new Crke(Igra.jezik);
      
      JTextField input = new JTextField(50); 
      input.addActionListener(this);
      input.addKeyListener(this);
      input.setSize(new Dimension(30,20));
      input.setFont(new Font("TimesRoman", Font.BOLD, 15));
      
      JPanel up = new JPanel();
      up.setSize(new Dimension(1000, 100));
      JButton b = new JButton("NOVA IGRA");
      b.addActionListener(this);
      b.setBounds(200, 100, 300, 50);
      JButton bJezik = new JButton("SPREMENI JEZIK");
      bJezik.addActionListener(this);
      
      up.add(b, BorderLayout.LINE_START);
      up.add(bJezik, BorderLayout.CENTER);
      
      JPanel bottom = new JPanel();
      bottom.setSize(new Dimension(1000, 100));
      bottom.add(input, BoxLayout.X_AXIS);
      
      add(platno1, BorderLayout.LINE_START);
      add(platno2, BorderLayout.CENTER);
      add(crke, BorderLayout.LINE_END);
      add(bottom, BorderLayout.AFTER_LAST_LINE);
      add(up, BorderLayout.PAGE_START);
      
   }

@Override
public void actionPerformed(ActionEvent e) {
	String text_l = e.getActionCommand();
	String text = text_l.toLowerCase();
	Igra.posodobi_in_odigraj(text);
	repaint();
}


@Override
public void keyReleased(KeyEvent e) {
	
}
@Override
public void keyTyped(KeyEvent e) {
    char keyChar = e.getKeyChar();
    if (Character.isLowerCase(keyChar)) {
      e.setKeyChar(Character.toUpperCase(keyChar));
    }
  }

@Override
public void keyPressed(KeyEvent e) {
	// TODO Auto-generated method stub
}
}

@SuppressWarnings("serial")
//---------------------------------------------------
class Crke extends JPanel{
	
	Jezik jezik;
	String[] crkeIzpis;
	int crkeVrstica;
	
	BarveCrke barveCrke;
	
	public Crke(Jezik jezik) {
		setPreferredSize(new Dimension(400, 400));
		barveCrke = Igra.barveCrke;
		
		if (jezik == Jezik.ANG) {
			crkeIzpis = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
			crkeVrstica = 4;
		}
		else {
			crkeIzpis = "ABCČDEFGHIJKLMNOPRSŠTUVZŽ".split("");
			crkeVrstica = 5;
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
		   
		   int x = 50;
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
				   x = 50;
				   y += 50;
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
   

@SuppressWarnings("serial")
class Platno extends JPanel {
	int st_plosce;
	Stanje stanje;
	private Polje[][] barve_polja;
	private String[][] plosca_vrednosti;
	LinkedList<Integer> steviloBesed;
	
   public Platno(int st_plosce) {
      setPreferredSize(new Dimension(600, 600));
      stanje = Igra.stanje;
      this.st_plosce = st_plosce;

      if (st_plosce == 1) {
    	 barve_polja = Igra.plosca1_barve;
    	 plosca_vrednosti = Igra.plosca1_vrednosti;
    	 steviloBesed = Igra.steviloBesed1;
      }
      else {
    	 barve_polja = Igra.plosca2_barve;
    	 plosca_vrednosti = Igra.plosca2_vrednosti;
    	 steviloBesed = Igra.steviloBesed2;
      }
   }
   
   public void paint(Graphics g) {
       super.paint(g);
       draw(g);
   }

   public void draw(Graphics g){
	   StanjeEnum stanjeEnum;
	   if (st_plosce == 1) {
		   stanjeEnum = stanje.plosca1;
	   }
	   else {
		   stanjeEnum = stanje.plosca2;
	   }
	   if (stanjeEnum == StanjeEnum.PORAZ) {
		   setBackground(new Color(255, 150, 150));
	   }
	   else if(stanjeEnum == StanjeEnum.ZMAGA) {
		   setBackground(new Color(150, 255, 150));
	   }
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
		   if (barve_polja[i][0] != Polje.PRAZNO) {
			   if (st_plosce == 1) {
				   String moznostiStr = Integer.toString(steviloBesed.get(i));
				   g.drawString(moznostiStr, x + 10, y + 50);
			   }
			   else {
				   String moznostiStr = Integer.toString(steviloBesed.get(i));
				   g.drawString(moznostiStr, x + 10, y + 50);
			   }
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


