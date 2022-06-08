package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import logika.Igra;
import logika.Jezik;
import logika.StanjeEnum;

@SuppressWarnings("serial")
public class Okno extends JFrame implements ActionListener, KeyListener{

   private Platno platno1;
   private Platno platno2;
   private Crke crke;
   protected static Igra igra;
   protected static JButton gumbNovaIgra;
   protected static JButton gumbSpremeniJezik;
   protected static JButton gumbVnos;
   protected static JTextField vnos;
   protected JEditorPane jep;
  

   public Okno() throws IOException {
      super();
      setTitle("Besedna igra");

      igra = Igra.novaIgra(Jezik.SLO);
     
      platno1 = new Platno(1);
      platno2 = new Platno(2);     
      crke = new Crke(igra);
      
      JPanel up = new JPanel();
      up.setSize(new Dimension(1000, 100));
      
      vnos = new JTextField(8); 
      vnos.addActionListener(this);    
      vnos.addKeyListener(this);
      vnos.setFont(new Font("TimesRoman", Font.BOLD, 25));
      vnos.setHorizontalAlignment(JTextField.CENTER);
      
      gumbNovaIgra = new JButton("NOVA IGRA");
      
      gumbNovaIgra.setPreferredSize(new Dimension(180, 40));
      gumbNovaIgra.setBackground(new Color(175, 170, 170));
      gumbNovaIgra.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 25));
      gumbNovaIgra.setForeground(Color.BLACK);
      gumbNovaIgra.addActionListener(this);

      gumbSpremeniJezik = new JButton("SPREMENI JEZIK", pomanjsanaIkona("slo_zastava.png"));
      gumbSpremeniJezik.setPreferredSize(new Dimension(310, 40));

      gumbSpremeniJezik.setBackground(new Color(175, 170, 170));
      gumbSpremeniJezik.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 25));
      gumbSpremeniJezik.setForeground(Color.BLACK);
      gumbSpremeniJezik.addActionListener(this);
  
      up.add(gumbNovaIgra, BorderLayout.LINE_START);
      up.add(gumbSpremeniJezik, BorderLayout.CENTER);
      
      jep = new JEditorPane();
      jep.setContentType("text/html");
      jep.setEditable(false);
      jep.setOpaque(false);

      JPanel bottom = new JPanel();
      bottom.setSize(new Dimension(1000, 100));
    
      gumbVnos= new JButton("VNESI");
      
      getRootPane().setDefaultButton(gumbVnos);
      gumbVnos.setPreferredSize(new Dimension(120, 40));
      gumbVnos.setBackground(new Color(175, 170, 170));
      gumbVnos.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 25));
      gumbVnos.setForeground(Color.BLACK);
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
   
	
   
   	public ImageIcon pomanjsanaIkona(String niz) {
   		ImageIcon imageIcon = new ImageIcon(niz);
        Image image = imageIcon.getImage(); 
        Image newimg = image.getScaledInstance(40, 25,  java.awt.Image.SCALE_SMOOTH);   
        imageIcon = new ImageIcon(newimg);
        return imageIcon;
   	}
   	
   	public void dodajUrl(String beseda1, String beseda2, JEditorPane jep, String url, String besedilo) {
   		jep.setText(besedilo);
        
        jep.addHyperlinkListener(new HyperlinkListener() {
          public void hyperlinkUpdate(HyperlinkEvent hle) {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(hle.getEventType())) {
              if (hle.getDescription().equalsIgnoreCase(url + beseda1)) {
  				try {
  					Desktop.getDesktop().browse(hle.getURL().toURI());
  				} catch (IOException | URISyntaxException e) {
  					e.printStackTrace();
  				}
              }
              if (hle.getDescription().equalsIgnoreCase(url + beseda2)) {
              	try {
              		Desktop.getDesktop().browse(hle.getURL().toURI());
  				} catch (IOException | URISyntaxException e) {
  					e.printStackTrace();
  				}
              }
            }
          }
        });
   	}
   	
   	public void izpisiSporocilo(Jezik jezik, StanjeEnum stanje) {
   		if (stanje == StanjeEnum.ZMAGA) {
   			if (jezik == Jezik.ANG) {
   				JOptionPane.showMessageDialog(platno2, "CONGRATULATIONS!");
   			}
   			else JOptionPane.showMessageDialog(platno2, "BRAVO!");
   		}
   		else {
   			if (jezik == Jezik.ANG) {
   				String url = "https://www.merriam-webster.com/dictionary/";
   				String tekst = ":( The correct solution is <a href='"+url+igra.beseda1+"'>"+igra.beseda1+"</a> and <a href='"+url+igra.beseda2+"'>"+igra.beseda2+"</a>.";
   			   	dodajUrl(igra.beseda1, igra.beseda2, jep, url, tekst);
   				JOptionPane.showMessageDialog(platno2, jep);
   			}
   			else {
   				String url = "https://fran.si/iskanje?View=1&Query=";
   				String tekst = ":( Iskani besedi sta <a href='"+url+igra.beseda1+"'>"+igra.beseda1+"</a> in <a href='"+url+igra.beseda2+"'>"+igra.beseda2+"</a>.";
   			   	dodajUrl(igra.beseda1, igra.beseda2, jep, url, tekst);
   				JOptionPane.showMessageDialog(platno2, jep);
   			}
   				
   		}
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
					;
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
        			if (igra.stanje_celota() != StanjeEnum.V_TEKU) izpisiSporocilo(igra.jezik, igra.stanje_celota());
        			
    			}
				vnos.setText("");
			}
		}
	else if (e.getSource() == gumbNovaIgra) {
		igra = Igra.novaIgra(igra.jezik);
		repaint();
	}
	else if (e.getSource() == gumbSpremeniJezik) {
    	if (igra.jezik == Jezik.ANG) {
    		igra = Igra.novaIgra(Jezik.SLO);
    		gumbNovaIgra.setText("NOVA IGRA");
    		gumbSpremeniJezik.setText("SPREMENI JEZIK");
    		gumbSpremeniJezik.setIcon(pomanjsanaIkona("slo_zastava.png"));
    		gumbSpremeniJezik.setPreferredSize(new Dimension(310, 40));
    		gumbVnos.setText("VNESI");
    		repaint();
    	} 
    	else {
    		igra = Igra.novaIgra(Jezik.ANG);
    		gumbNovaIgra.setText("NEW GAME");
    		gumbSpremeniJezik.setText("CHANGE LANGUAGE");
    		gumbSpremeniJezik.setIcon(pomanjsanaIkona("ang_zastava.png"));
    		gumbSpremeniJezik.setPreferredSize(new Dimension(330, 40));
    		gumbVnos.setText("ENTER");
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
