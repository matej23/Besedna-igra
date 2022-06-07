package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logika.Igra;
import logika.Jezik;

@SuppressWarnings("serial")
class Okno extends JFrame implements ActionListener, KeyListener{

   private Platno platno1;
   private Platno platno2;
   protected static Igra igra;
   protected static JButton gumbNovaIgra;
   protected static JButton gumbSpremeniJezik;
   protected static JButton gumbVnos;
   protected static JTextField vnos;

  

   public Okno() throws IOException {
      super();
      setTitle("Besedna igra");
      
      igra = Igra.novaIgra(Jezik.SLO);
 
      platno1 = new Platno(1);
      platno2 = new Platno(2);     
      Crke crke = new Crke(igra);
      
      JPanel up = new JPanel();
      up.setSize(new Dimension(1000, 100));
      
      vnos = new JTextField(8); 
      vnos.addActionListener(this);    
      vnos.addKeyListener(this);
      vnos.setFont(new Font("TimesRoman", Font.BOLD, 25));
      vnos.setHorizontalAlignment(JTextField.CENTER);
      
      if (igra.jezik == Jezik.ANG) {
    	  gumbNovaIgra = new JButton("NEW GAME");
      }
      else {
    	  gumbNovaIgra = new JButton("NOVA IGRA");
      }
      gumbNovaIgra.setPreferredSize(new Dimension(180, 40));
      gumbNovaIgra.setBackground(new Color(175, 170, 170));
      gumbNovaIgra.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 25));
      gumbNovaIgra.setForeground(Color.BLUE);
      gumbNovaIgra.addActionListener(this);

      if (igra.jezik == Jezik.ANG) {
    	  gumbSpremeniJezik = new JButton("CHANGE LANGUAGE");
    	  gumbSpremeniJezik.setPreferredSize(new Dimension(290, 40));
      }
      else {
    	  gumbSpremeniJezik = new JButton("SPREMENI JEZIK");
    	  gumbSpremeniJezik.setPreferredSize(new Dimension(250, 40));
      }
      gumbSpremeniJezik.setBackground(new Color(175, 170, 170));
      gumbSpremeniJezik.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 25));
      gumbSpremeniJezik.setForeground(Color.BLUE);
      gumbSpremeniJezik.addActionListener(this);
  
      up.add(gumbNovaIgra, BorderLayout.LINE_START);
      up.add(gumbSpremeniJezik, BorderLayout.CENTER);
      
      if (igra.jezik == Jezik.SLO) {
          BufferedImage myPicture = ImageIO.read(new File("slo_zastava.png"));
          BufferedImage myPicture_scale = scale(myPicture, 70, 40);
          JLabel picture = new JLabel(new ImageIcon(myPicture_scale));
          
          up.add(picture);
      }
      else {
          BufferedImage myPicture = ImageIO.read(new File("ang_zastava.png"));
          BufferedImage myPicture_scale = scale(myPicture, 70, 40);
          JLabel picture = new JLabel(new ImageIcon(myPicture_scale));
          up.add(picture);
      }
      JPanel bottom = new JPanel();
      bottom.setSize(new Dimension(1000, 100));
    
      if (igra.jezik == Jezik.ANG) {
    	  gumbVnos= new JButton("ENTER");
      }
      else {
    	  gumbVnos= new JButton("VNESI");
      }
      getRootPane().setDefaultButton(gumbVnos);
      gumbVnos.setPreferredSize(new Dimension(120, 40));
      gumbVnos.setBackground(new Color(175, 170, 170));
      gumbVnos.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 25));
      gumbVnos.setForeground(Color.WHITE);
      gumbVnos.addActionListener(this);
    	  
      bottom.add(vnos);
      bottom.add(gumbVnos);
      
      add(platno1, BorderLayout.LINE_START);
      add(platno2, BorderLayout.CENTER);
      add(crke, BorderLayout.LINE_END);
      add(bottom, BorderLayout.AFTER_LAST_LINE);
      add(up, BorderLayout.PAGE_START);
      rootPane.setDefaultButton(gumbVnos);
        
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
   
	
   public BufferedImage scale(BufferedImage imageToScale, int dWidth, int dHeight) {
       BufferedImage scaledImage = null;
       if (imageToScale != null) {
           scaledImage = new BufferedImage(dWidth, dHeight, imageToScale.getType());
           Graphics2D graphics2D = scaledImage.createGraphics();
           graphics2D.drawImage(imageToScale, 0, 0, dWidth, dHeight, null);
           graphics2D.dispose();
       }
       return scaledImage;
   }
   

@Override
public void actionPerformed(ActionEvent e) {
	if ((e.getSource() == vnos) || (e.getSource() == gumbVnos)) {
			String textU = vnos.getText();
			String textL = textU.toLowerCase();
			if (textL.length() > 5) {
				vnos.setText("");
				if (igra.jezik == Jezik.ANG) {
					JOptionPane.showMessageDialog(platno2, "WORD YOU ENTERED IS TOO LONG");
				}
				else {
					JOptionPane.showMessageDialog(platno2, "BESEDA, KI STE JO VNESLI, JE PREDOLGA");
				}
			}
			else if (textL.length() < 5) {
				vnos.setText("");
				if (igra.jezik == Jezik.ANG) {
					JOptionPane.showMessageDialog(platno2, "WORD YOU ENTERED IS TOO SHORT");
				}
				else {
					JOptionPane.showMessageDialog(platno2, "BESEDA, KI STE JO VNESLI, JE PREKRATKA");
				}
			}
			else {
				if (!Igra.LST.contains(textL)) {
					vnos.setText("");
					if (igra.jezik == Jezik.ANG) {
						JOptionPane.showMessageDialog(platno2, "WORD YOU ENTERED IS NOT IN DICTIONARY");
					}
					else {
						JOptionPane.showMessageDialog(platno2, "VNEŠENE BESEDE NI V SLOVARJU");
					}
				}
    			else {
        			igra.posodobi_in_odigraj(textL);
        			repaint();
        			vnos.setText("");
    			}
			}
		}
	else if (e.getSource() == gumbNovaIgra) {
		igra = Igra.novaIgra(igra.jezik);
		repaint();
	}
	else if (e.getSource() == gumbSpremeniJezik) {
    	if (igra.jezik == Jezik.ANG) {
    		igra = Igra.novaIgra(Jezik.SLO);
    		repaint();
    	}
    	else {
    		igra = Igra.novaIgra(Jezik.ANG);
    		repaint();
    	}
    }
	 
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
}

}


