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
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import logika.Igra;
import logika.Jezik;
import logika.Polje;


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
      
      JPanel bottom = new JPanel();
      bottom.setSize(new Dimension(1000, 100));
      bottom.add(input, BoxLayout.X_AXIS);
      
//      Border blackline = BorderFactory.createLineBorder(Color.black);
//      bottom.setBorder(blackline);

      
      add(platno1, BorderLayout.LINE_START);
      add(platno2, BorderLayout.CENTER);
      add(crke, BorderLayout.LINE_END);
      add(bottom, BorderLayout.AFTER_LAST_LINE);
      
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
	
	HashMap<String, Polje> barve1= new HashMap<String, Polje>();
	HashMap<String, Polje> barve2= new HashMap<String, Polje>();
	
	private Polje[][] barve_polja1;
	private String[][] plosca_vrednosti1;
	private Polje[][] barve_polja2;
	private String[][] plosca_vrednosti2;
	
	public Crke(Jezik jezik) {
		setPreferredSize(new Dimension(400, 400));
	    barve_polja1 = Igra.plosca1_barve;
	    plosca_vrednosti1 = Igra.plosca1_vrednosti;
	    
	    barve_polja2 = Igra.plosca2_barve;
	    plosca_vrednosti2 = Igra.plosca2_vrednosti;

		if (jezik == Jezik.ANG) {
			crkeIzpis = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
			crkeVrstica = 6;
			for (String crka : crkeIzpis) {
				barve1.put(crka, Polje.NAPACNO);
				barve2.put(crka, Polje.NAPACNO);
			}
		}
		else {
			crkeIzpis = "ABCČDEFGHIJKLMNOPRSŠTUVZŽ".split("");
			crkeVrstica = 5;
			for (String crka : crkeIzpis) {
				barve1.put(crka, Polje.NAPACNO);
				barve2.put(crka, Polje.NAPACNO);
			}
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
		   int y = 50;
		   
		   for (int i = 0; i < plosca_vrednosti1.length; i++) {
			   for (int j = 0; j < plosca_vrednosti1[0].length; j++) {
				   if (barve_polja1[i][j] == Polje.PRAVILNO) {
					   barve1.put(plosca_vrednosti1[i][j], Polje.PRAVILNO);
				   }
				   else if (barve_polja1[i][j] == Polje.NAPACNO) {
					   barve1.put(plosca_vrednosti1[i][j], Polje.NAPACNO);
				   }
				   else if (barve_polja1[i][j] == Polje.DELNOPRAVILNO) {
					   if (barve1.get(plosca_vrednosti1[i][j]) == Polje.PRAZNO) {
						   barve1.put(plosca_vrednosti1[i][j], Polje.DELNOPRAVILNO);
					   }
					   else {
						   continue;
					   }
				   }
				   else {
					   continue;
				   }
			   }
		   }
		   for (int k = 0; k < plosca_vrednosti2.length; k++) {
			   for (int l = 0; l < plosca_vrednosti2[0].length; l++) {
				   if (barve_polja2[k][l] == Polje.PRAVILNO) {
					   barve2.put(plosca_vrednosti2[k][l], Polje.PRAVILNO);
				   }
				   else if (barve_polja2[k][l] == Polje.NAPACNO) {
					   barve2.put(plosca_vrednosti2[k][l], Polje.NAPACNO);
				   }
				   else if (barve_polja2[k][l] == Polje.DELNOPRAVILNO) {
					   if (barve2.get(plosca_vrednosti2[k][l]) == Polje.PRAZNO) {
						   barve2.put(plosca_vrednosti2[k][l], Polje.DELNOPRAVILNO);
					   }
					   else {
						   continue;
					   }
				   }
				   else {
					   continue;
				   }
			   }
		   }
		   
		   int m = crkeVrstica;
		   
		   for (String crka : crkeIzpis) {
			   if (barve1.get(crka) == Polje.DELNOPRAVILNO) {
				   g.setColor(delno_pravilno);
			   }
			   else if (barve1.get(crka) == Polje.PRAVILNO) {
				   g.setColor(pravilno);
			   }
			   else if (barve1.get(crka) == Polje.NAPACNO) {
				   g.setColor(napacno);
			   }
			   else {
				   g.setColor(prazno);
			   }
			   
			   if (m > 0) {
				   g.fillRect(x, y, 45, 45);
				   g.setColor(Color.BLACK); 
				   Rectangle rectangle = new Rectangle(x,y,45,45);
				   centerString(g, rectangle,crka.toUpperCase(),new Font("SansSerif Bold", Font.PLAIN, 30));
				   m-= 1;
				   x += 50;
			   }
			   else {
				   x = 50;
				   y+= 50;
				   g.setColor(Color.BLACK);
				   g.fillRoundRect(x, y, 45, 45 , 5, 5);
				   Rectangle rectangle = new Rectangle(x,y,50,50);
				   centerString(g,rectangle,crka.toUpperCase(),new Font("SansSerif Bold", Font.PLAIN, 30));
				   m = crkeVrstica;
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
	private Polje[][] barve_polja;
	private String[][] plosca_vrednosti;
	LinkedList<Integer> steviloBesed;
	
   public Platno(int st_plosce) {
      setPreferredSize(new Dimension(600, 600));
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


