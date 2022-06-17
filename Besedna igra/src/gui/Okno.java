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
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.plaf.ColorUIResource;

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
   protected static JButton navodila;
   protected static JButton izhod;
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
      
      izhod = new JButton("IZHOD");
      
      izhod.setPreferredSize(new Dimension(150, 40));
      izhod.setBackground(new Color(175, 170, 170));
      izhod.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 25));
      
      ImageIcon izhodIcon = new ImageIcon("izhod.png");
      Image image = izhodIcon.getImage(); 
      Image newimg = image.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);   
      izhodIcon = new ImageIcon(newimg);
      
      izhod.setIcon(izhodIcon);
      izhod.setForeground(Color.BLACK);
      izhod.addActionListener(this);
      
      gumbNovaIgra = new JButton("NOVA IGRA");
      
      gumbNovaIgra.setPreferredSize(new Dimension(180, 40));
      gumbNovaIgra.setBackground(new Color(175, 170, 170));
      gumbNovaIgra.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 25));
      gumbNovaIgra.setForeground(Color.BLACK);
      gumbNovaIgra.addActionListener(this);
      
      navodila = new JButton("POMOČ");
      
      navodila.setPreferredSize(new Dimension(160, 40));
      navodila.setBackground(new Color(175, 170, 170));
      navodila.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 25));
      
      ImageIcon pomocIcon = new ImageIcon("pomoc.png");
      Image pomocImage = pomocIcon.getImage(); 
      Image newImage = pomocImage.getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH);   
      pomocIcon = new ImageIcon(newImage);
      
      navodila.setIcon(pomocIcon);
      navodila.setForeground(Color.BLACK);
      navodila.addActionListener(this);

      gumbSpremeniJezik = new JButton("SPREMENI JEZIK", pomanjsanaIkona("slo_zastava.png"));
      gumbSpremeniJezik.setPreferredSize(new Dimension(310, 40));

      gumbSpremeniJezik.setBackground(new Color(175, 170, 170));
      gumbSpremeniJezik.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 25));
      gumbSpremeniJezik.setForeground(Color.BLACK);
      gumbSpremeniJezik.addActionListener(this);
  
      up.add(gumbNovaIgra, BorderLayout.LINE_START);
      up.add(gumbSpremeniJezik, BorderLayout.CENTER);
      up.add(navodila, BorderLayout.LINE_END);
      up.add(izhod, BorderLayout.LINE_END);
 
      jep = new JEditorPane();
      jep.setContentType("text/html");
      jep.setFont((new Font("TimesRoman", Font.CENTER_BASELINE, 30)));
      jep.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);
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
              if (hle.getDescription().equalsIgnoreCase(url+beseda1)) {
  				try {
  					Desktop.getDesktop().browse(hle.getURL().toURI());
  				} catch (IOException | URISyntaxException e) {
  					e.printStackTrace();
  				}
              }
              else if (hle.getDescription().equalsIgnoreCase(url+beseda2)) {
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
   	
   	public void izpisiSporocilo(Jezik jezik, StanjeEnum stanje) throws MalformedURLException {
   		if (stanje == StanjeEnum.ZMAGA) {
 			UIManager.put("OptionPane.background",new ColorUIResource(204, 255, 204));
   			UIManager.put("Panel.background",new ColorUIResource(204, 255, 204));
   			UIManager.put("OptionPane.messageFont", new Font("TimesRoman", Font.CENTER_BASELINE, 30));
   			UIManager.put("OptionPane.buttonFont", new Font("TimesRoman", Font.CENTER_BASELINE, 25));
   			UIManager.put("OptionPane.minimumSize",new Dimension(400,200)); 
   			UIManager.put("Button.background", Color.WHITE);
   			
     		ImageIcon imageIcon = new ImageIcon("congrats.png");
            Image image = imageIcon.getImage(); 
            Image newimg = image.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);   
            imageIcon = new ImageIcon(newimg);
            
   			if (jezik == Jezik.ANG) {
   				Object[] options = {"OK", "NEW GAME", "EXIT"};
   				int result = JOptionPane.showOptionDialog(null, "CONGRATULATIONS!", "YOU WON !", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, imageIcon, options, options[0]);
   				if (result == 1) {
   					gumbNovaIgra.doClick();
   				}
   				else if (result == 2) {
   					System.exit(0);
   				}
   			}
   			
   			else {
   				Object[] options = {"OK", "NOVA IGRA", "IZHOD"};
   				int result = JOptionPane.showOptionDialog(null, "BRAVO !", "ZMAGALI STE !", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, imageIcon, options, options[0]);
   				if (result == 1) {
   					gumbNovaIgra.doClick();
   				}
   				else if (result == 2) {
   					System.exit(0);
   				}
   			}
   		}
   		
   		else {
			UIManager.put("OptionPane.background",new ColorUIResource(255, 204, 204));
			UIManager.put("Panel.background",new ColorUIResource(255, 204, 204));
   			UIManager.put("OptionPane.minimumSize",new Dimension(400,200));
   			UIManager.put("Button.background", Color.WHITE);
   			
     		ImageIcon imageIcon = new ImageIcon("dict.png");
            Image image = imageIcon.getImage(); 
            Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);   
            imageIcon = new ImageIcon(newimg);

   			if (jezik == Jezik.ANG) {
   				String url = "https://www.merriam-webster.com/dictionary/";
   				String tekst = "THE CORRECT SOLUTION IS <a href='"+url+igra.beseda1+"'>"+igra.beseda1.toUpperCase()+"</a> AND <a href='"+url+igra.beseda2+"'>"+igra.beseda2.toUpperCase()+"</a>.";
   			   	dodajUrl(igra.beseda1, igra.beseda2, jep, url, tekst);
   			   	
   			   	Object[] options = {"OK", "NEW GAME", "EXIT"};
   				int result = JOptionPane.showOptionDialog(null, jep, "YOU LOST...", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, imageIcon, options, options[0]);
   				if (result == 1) {
   					gumbNovaIgra.doClick();
   				}
   				else if (result == 2) {
   					System.exit(0);
   				}
   			}
   			else {
   				String url = "https://fran.si/iskanje?View=1&Query=";
   				String tekst = " ISKANI BESEDI STA <a href='"+url+igra.beseda1+"'>"+igra.beseda1.toUpperCase()+"</a> IN <a href='"+url+igra.beseda2+"'>"+igra.beseda2.toUpperCase()+"</a>.";
   			   	dodajUrl(igra.beseda1, igra.beseda2, jep, url, tekst);
   			   	
   			   	Object[] options = {"OK", "NOVA IGRA", "IZHOD"};
   				int result = JOptionPane.showOptionDialog(null, jep, "IZGUBILI STE...", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, imageIcon, options, options[0]);
   				if (result == 1) {
   					gumbNovaIgra.doClick();
   				}
   				else if (result == 2) {
   					System.exit(0);
   				}
   			}
   				
   		}
   	}
   	
   	@Override
	public void actionPerformed(ActionEvent e) {
		if ((e.getSource() == vnos) || (e.getSource() == gumbVnos)) {
			
	 		ImageIcon napakaIcon = new ImageIcon("napaka.png");
	        Image image = napakaIcon.getImage(); 
	        Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);   
	        napakaIcon = new ImageIcon(newimg);
	        
			UIManager.put("OptionPane.background",new ColorUIResource(255, 204, 204));
			UIManager.put("Panel.background",new ColorUIResource(255, 204, 204));
	   		UIManager.put("OptionPane.messageFont", new Font("TimesRoman", Font.CENTER_BASELINE, 30));
	   		UIManager.put("OptionPane.buttonFont", new Font("TimesRoman", Font.CENTER_BASELINE, 25));
	   		UIManager.put("OptionPane.minimumSize",new Dimension(300,200));
			UIManager.put("Button.background", Color.WHITE);
			
			String textU = vnos.getText();
			String textL = textU.toLowerCase();
				
				if (textL.length() > 5) {
					vnos.setText("");
					if (igra.jezik == Jezik.ANG) {
						JOptionPane.showMessageDialog(null, "WORD YOU ENTERED IS TOO LONG", textU, JOptionPane.ERROR_MESSAGE, napakaIcon);
					}
					else {
						JOptionPane.showMessageDialog(null, "BESEDA, KI STE JO VNESLI, JE PREDOLGA", textU, JOptionPane.ERROR_MESSAGE, napakaIcon);
					}
				}
				else if (textL.length() < 5) {
					vnos.setText("");
					if (igra.jezik == Jezik.ANG) {
						JOptionPane.showMessageDialog(null, "WORD YOU ENTERED IS TOO SHORT", textU, JOptionPane.ERROR_MESSAGE, napakaIcon);
					}
					else {
						JOptionPane.showMessageDialog(null, "BESEDA, KI STE JO VNESLI, JE PREKRATKA", textU, JOptionPane.ERROR_MESSAGE, napakaIcon);
					}
				}
				else {
					if (!Igra.LST.contains(textL)) {
					   	
						if (igra.jezik == Jezik.ANG) {
							JOptionPane.showMessageDialog(null, "WORD YOU ENTERED IS NOT IN DICTIONARY",textU, JOptionPane.ERROR_MESSAGE, napakaIcon);
						}
						else {
							JOptionPane.showMessageDialog(null, "VNEŠENE BESEDE NI V SLOVARJU",textU, JOptionPane.ERROR_MESSAGE, napakaIcon);
						}
					}
	    			else {
	        			igra.posodobi_in_odigraj(textL);
	        			repaint();
	        			if (igra.stanje_celota() != StanjeEnum.V_TEKU)
							try {
							     gumbVnos.setVisible(false);
							     vnos.setVisible(false);
								izpisiSporocilo(igra.jezik, igra.stanje_celota());
							} catch (MalformedURLException e1) {
								e1.printStackTrace();
							}
	        			
	    			}
					vnos.setText("");
				}
			}
		else if (e.getSource() == gumbNovaIgra) {
			igra = Igra.novaIgra(igra.jezik);
		    gumbVnos.setVisible(true);
		    vnos.setVisible(true);
			repaint();

		}
		else if (e.getSource() == gumbSpremeniJezik) {
	    	if (igra.jezik == Jezik.ANG) {
	    		igra = Igra.novaIgra(Jezik.SLO);
	    		gumbNovaIgra.setText("NOVA IGRA");
	    		izhod.setText("IZHOD");
	    		gumbSpremeniJezik.setText("SPREMENI JEZIK");
	    		navodila.setText("POMOČ");
	    		gumbSpremeniJezik.setIcon(pomanjsanaIkona("slo_zastava.png"));
	    		gumbSpremeniJezik.setPreferredSize(new Dimension(310, 40));
	    		gumbVnos.setText("VNESI"); 
	    	    gumbVnos.setVisible(true);
	    	    vnos.setVisible(true);
	    		repaint();
	    	} 
	    	else {
	    		igra = Igra.novaIgra(Jezik.ANG);
	    		gumbNovaIgra.setText("NEW GAME");
	    		izhod.setText("EXIT");
	    		gumbSpremeniJezik.setText("CHANGE LANGUAGE");
	    		navodila.setText("HELP");
	    		gumbSpremeniJezik.setIcon(pomanjsanaIkona("ang_zastava.png"));
	    		gumbSpremeniJezik.setPreferredSize(new Dimension(330, 40));
	    		gumbVnos.setText("ENTER");
	    	    gumbVnos.setVisible(true);
	    	    vnos.setVisible(true);
	    		repaint();
	    	}
	    }
		else if (e.getSource() == izhod) {
			System.exit(0);
		}
		else if (e.getSource() == navodila) {
			UIManager.put("OptionPane.background",new ColorUIResource(255, 255, 180));
			UIManager.put("Panel.background",new ColorUIResource(255, 255, 180));
	   		UIManager.put("OptionPane.messageFont", new Font("TimesRoman", Font.CENTER_BASELINE, 20));
	   		UIManager.put("OptionPane.buttonFont", new Font("TimesRoman", Font.CENTER_BASELINE, 20));
	   		UIManager.put("OptionPane.minimumSize",new Dimension(300,200));
			UIManager.put("Button.background", Color.WHITE);
			
			ImageIcon pomocIcon = new ImageIcon("pomoc.png");
			Image pomocImage = pomocIcon.getImage(); 
		    Image newImage = pomocImage.getScaledInstance(35, 35,  java.awt.Image.SCALE_SMOOTH);   
		    pomocIcon = new ImageIcon(newImage);
		    
		    String textAng = "Before you, stands iteration of popular game Wordle.\n"
		    		+ "\n"
		    		+ "The purpose of the game is to guess two randomly chosen words, based on the feedback given by the computer on every round.\n"
		    		+ "Words are chosen by the computer from the dictionary with five-letter words.\n"
		    		+ "Each word belongs to one board.\n"
		    		+ "A player types a five-letter word into the input box and submits it via Enter click or button press.\n"
		    		+ "The player's guess will be written on both boards and letters will get colored based on the correct word of the given board.\n"
		    		+ "If a letter turns green, then the letter is in the correct place for the password of the given board.\n"
		    		+ "If a letter turns yellow, then the letter is in the password of the given board but not in the correct place. \n"
		    		+ "If a letter turns dark grey, then it is not in the password of the given board.\n"
		    		+ "If any letter in the try repeats itself, then the computer will look up how many times the letter repeats itself in the password.\nThen only the most accurate positions will get colored.\n"
		    		+ "A player wins by guessing both words in less then 6 tries and loses if he runs out of tries before guessing both passwords.\n"
		    		+ "\n"
		    		+ "FOOTNOTE:\n" + "Purpose of the alphabet by the side is to help with organisation of the used and unused letters and their values of the given board.\n"
		    		+ "\n"
		    		+ "Have fun while playing :)";
		    
		    String textSlo = "Pred vami je iteracija igre Wordle oziroma Besedle.\n"
		    		+ "\n"
		    		+ "Namen igre je na podlagi namigov in povratnih informacij, ki jih vračata plošči, uganiti gesli, ki si ju je zamislil računalnik.\n"
		    		+ "Gesli sta naključno izbrani iz slovarja besed s petimi črkami.\n"
		    		+ "Vsako izmed gesel pripada eni izmed igralnih plošč.\n"
		    		+ "Igralec odigra potezo, ko besedo s petimi črkami iz slovarja vtipka v polje za vnos in vnos potrdi s klikom na gumb ali tipko Enter.\n"
		    		+ "Beseda se bo zapisala v obe plošči hkrati, črke pa se bodo na podlagi gesla za tisto ploščo primerno obarvale.\n"
		    		+ "Če se črka obarva zeleno, je v besedi na pravilnem mestu.\n"
		    		+ "Če se črka obarva rumeno, je v besedi, vendar na napačnem mestu.\n"
		    		+ "Če se črka obarva temno sivo, to pomeni, da je ni v besedi.\n"
		    		+ "Če se v poskusu katera izmed črk pojavi večkrat, računalnik preveri koliko ponovitev te črke je v geslu in obarva le najustreznejše.\n"
		    		+ "Igralec zmaga, če v 6 poskusih ugotovi obe besedi in izgubi, če zmanjka poskusov.\n"
		    		+ "\n"
		    		+ "OPOMBA:\n" + "Abeceda ob strani je namenjena lažjemu pregledu uporabljenih in neuporabljenih črk na levi oziroma desni plošči. \n"
		    		+ "\n"
		    		+ "Uživajte v igranju :)";
		    		
			if (igra.jezik == Jezik.ANG) {
				JOptionPane.showMessageDialog(null, textAng, "INSTRUCTIONS", JOptionPane.ERROR_MESSAGE, pomocIcon);
			}
			else {
				JOptionPane.showMessageDialog(null, textSlo, "NAVODILA", JOptionPane.INFORMATION_MESSAGE, pomocIcon);
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
