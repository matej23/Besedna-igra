package logika;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class Igra {
	
	
	public static final int V = 6;
	public static final int S = 5;
	
	public LinkedList<String> LST; 
	
	public static String[][] plosca1_vrednosti;
	public static String[][] plosca2_vrednosti;
	public static Polje[][] plosca1_barve;
	public static Polje[][] plosca2_barve;
	
	protected static Stanje stanje;
	
	protected static String beseda1;
	protected static String beseda2;
	
	protected LinkedList<String> zadoscajo1;
	protected LinkedList<String> zadoscajo2;
	protected Jezik jezik;
	protected ColorTheme color;
	protected Cas cas;
	
	//Jezik jezik
	@SuppressWarnings("unchecked")
	public Igra(Jezik jezik) {
		this.jezik = jezik;
		
		if (jezik == Jezik.ANG) {
			{
				try {
					LST = seznam_besed("besede_ang.txt");
				}
				catch (IOException ex){
					System.out.println("Datoteke besede.txt ne najde");
				}
			}
		}
		else {
			{
				try {
					LST = seznam_besed("besede_slo.txt");
				}
				catch (IOException ex){
					System.out.println("Datoteke besede.txt ne najde");
				}
			}
		}
		zadoscajo1 = (LinkedList<String>) LST.clone();
		zadoscajo2 = (LinkedList<String>) LST.clone();
		
		stanje = new Stanje(LST.size());
		Random rand = new Random();
		int i = rand.nextInt(LST.size());
		beseda1 = LST.get(i);
		
		LinkedList<String> lst = new LinkedList<String>();
		lst = (LinkedList<String>) LST.clone();
		lst.remove(beseda1);
		
		int j = rand.nextInt(lst.size());
		beseda2 = lst.get(j);
		
		plosca1_vrednosti = new String[V][S];
		plosca2_vrednosti = new String[V][S];
		plosca1_barve = new Polje[V][S];
		plosca2_barve = new Polje[V][S];
		for (int v = 0; v < V; v++) {
			for (int s = 0; s < S; s++) {
				plosca1_barve[v][s] = Polje.PRAZNO;
				plosca2_barve[v][s] = Polje.PRAZNO;
				plosca1_vrednosti[v][s] = "";
				plosca2_vrednosti[v][s] = "";
			}
		}
	}
	
	public static LinkedList<String> seznam_besed (String imeVhod) throws IOException {
		BufferedReader vhod = new BufferedReader (new FileReader(imeVhod));
		LinkedList<String> sez = new LinkedList<String>();
		while (vhod.ready()) {
			String vrstica = vhod.readLine();
			String[] str_lst = vrstica.split(",");
			for (int i = 0; i < str_lst.length; i++) {
				sez.add(str_lst[i]);
			}
		}
		vhod.close();
		return sez;
		
	}
	
	// upam, da zdej to ok dela
	public static Map<Integer, Polje> barva_za_vrednost(String geslo, String poskus) {
		Map<Integer, Polje> slovar = new HashMap<Integer, Polje>();
		
		String[] lst_poskus = poskus.split("");
		String[] lst_geslo = geslo.split("");
		
		for (int i = 0; i < poskus.length(); ++i) {
			if (lst_poskus[i].equals(lst_geslo[i])) {
				slovar.put(i, Polje.PRAVILNO);
				lst_geslo[i] = "0";
			}
		}
		for (int i = 0; i < poskus.length(); ++i) {
			int j = Arrays.asList(lst_geslo).indexOf(lst_poskus[i]);
			if (slovar.get(i) != Polje.PRAVILNO) {
				if (j != -1) {
				slovar.put(i, Polje.DELNOPRAVILNO);
				lst_geslo[j] = "0";
				}
				else slovar.put(i, Polje.NAPACNO);
			}
		}
		return slovar;
	}
	
	// funkcija, ki updejta vse stiri plosce glede na novo besedo 
	// (preveri, ali sta obe plosci v teku)
	
	public static void odigraj(String poskus) {
		String[] lst_poskus = poskus.split("");
		if ((stanje.plosca1 == StanjeEnum.V_TEKU) && (stanje.plosca2 == StanjeEnum.V_TEKU)){
			int vrstica = 0;
			while (plosca1_barve[vrstica][0] != Polje.PRAZNO) {
				vrstica++;
			}
			Map<Integer, Polje> barve_vrednosti1 = barva_za_vrednost(beseda1, poskus);
			Map<Integer, Polje> barve_vrednosti2 = barva_za_vrednost(beseda2, poskus);
			
			for (int i = 0; i < plosca1_barve[0].length; i++ ) {
				plosca1_barve[vrstica][i] = barve_vrednosti1.get(i);
				plosca2_barve[vrstica][i] = barve_vrednosti2.get(i);
				plosca1_vrednosti[vrstica][i] = lst_poskus[i];
				plosca2_vrednosti[vrstica][i] = lst_poskus[i];		
			}
		}
		
		else if (stanje.plosca1 == StanjeEnum.V_TEKU) {
			int vrstica = 0;
			while (plosca1_barve[vrstica][0] != Polje.PRAZNO) {
				vrstica++;
			}
			Map<Integer, Polje> barve_vrednosti1 = barva_za_vrednost(beseda1, poskus);
			
			for (int i = 0; i < plosca1_barve[0].length; i++ ) {
				plosca1_barve[vrstica][i] = barve_vrednosti1.get(i);
				plosca1_vrednosti[vrstica][i] = lst_poskus[i];
			}
		}
		
		else if (stanje.plosca2 == StanjeEnum.V_TEKU) {
			int vrstica = 0;
			while (plosca2_barve[vrstica][0] != Polje.PRAZNO) {
				vrstica++;
			}
			Map<Integer, Polje> barve_vrednosti2 = barva_za_vrednost(beseda2, poskus);
			
			for (int i = 0; i < plosca2_barve[0].length; i++ ) {
				plosca2_barve[vrstica][i] = barve_vrednosti2.get(i);
				plosca2_vrednosti[vrstica][i] = lst_poskus[i];		
			}
		}
		else {
			System.out.print("STANJE ZA VNOS POTEZE NI USTREZNO ! :(");
		}
	}
	
	
	// funkcija, ki preracuna stevilo moznih besed
	public int stevilo_moznih(String poskus, int st_plosca) {
		LinkedList<String> zadoscajo;
		Map<Integer, Polje> vrednosti;
		
		if (st_plosca == 1) {
			zadoscajo = zadoscajo1;
			vrednosti = barva_za_vrednost(beseda1, poskus);
		}
		else if (st_plosca == 2) {
			zadoscajo = zadoscajo2;
			vrednosti = barva_za_vrednost(beseda2, poskus);
		}
		else {
			System.out.print("TA PLOSCA NE OBSTAJA");
			return 0; 
		}
		
		String[] crke = poskus.split("");
		
		Map<String, Triple<Set<Integer>, Set<Integer>, Set<Integer>>> zahteve_crke = 
					new HashMap<String, Triple<Set<Integer>, Set<Integer>, Set<Integer>>>();
		
		for (Integer index : vrednosti.keySet()) {
			if (stevilo_ponovitev(crke, crke[index]) == 1) {
				int ind = poskus.indexOf(crke[index]);
				
				if (vrednosti.get(ind) == Polje.PRAVILNO) {
					Set<Integer> potrebna = new HashSet<Integer>(Arrays.asList(index));
					Set<Integer> mozna = new HashSet<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5));
					Set<Integer> stevilo = new HashSet<Integer>(Arrays.asList(1, 2, 3, 4, 5));
					
					Triple<Set<Integer>,Set<Integer>, Set<Integer>> triple_crke= new Triple<>(potrebna, mozna, stevilo);
					zahteve_crke.put(crke[index], triple_crke);
				}					
					
				else if (vrednosti.get(ind) == Polje.DELNOPRAVILNO) {
					Set<Integer> potrebna = new HashSet<Integer>();
					Set<Integer> mozna = new HashSet<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5));
					mozna.remove(ind);
					Set<Integer> stevilo = new HashSet<Integer>(Arrays.asList(1, 2, 3, 4, 5));
					Triple<Set<Integer>,Set<Integer>, Set<Integer>> triple_crke= new Triple<>(potrebna, mozna, stevilo);
					zahteve_crke.put(crke[ind], triple_crke);
					}
					
				else {//==Polje.NAPACNO
					Set<Integer> potrebna = new HashSet<Integer>();
					Set<Integer> mozna = new HashSet<Integer>();
					Set<Integer> stevilo = new HashSet<Integer>(Arrays.asList(0));
					Triple<Set<Integer>, Set<Integer>, Set<Integer>> triple_crke= new Triple<>(potrebna, mozna, stevilo);
					zahteve_crke.put(crke[ind], triple_crke);
					}
				}
			
				//vec ponovitev te crke(crke na tem indexu)
				else {
					LinkedList<Integer> indexi = ponovitve(crke, crke[index]);
					
					Set<Integer> mozna = new HashSet<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5));
					Set<Integer> potrebna = new HashSet<Integer>();
					Set<Integer> stevilo = new HashSet<Integer>();
					
					Set<Integer> ni_te_crke =  new HashSet<Integer>();
					int niso_sivi = indexi.size();
					
					for (int n = 0; n < indexi.size(); n++) {
						if (vrednosti.get(n) == Polje.NAPACNO) {
								mozna.remove(n);
								ni_te_crke.add(indexi.get(n));
								niso_sivi--;
						}
						
						else if (vrednosti.get(n) == Polje.DELNOPRAVILNO) {
							mozna.remove(n);
						}
						else {
							potrebna.add(indexi.get(n));
						}
					}
					
					if (niso_sivi == indexi.size()) {
						while (niso_sivi < 6) {
							stevilo.add(niso_sivi);
							niso_sivi++;
						}
					}
					else {
						stevilo.add(niso_sivi);
					}
					
					Triple<Set<Integer>,Set<Integer>, Set<Integer>> triple_crke= new Triple<>(potrebna, mozna, stevilo);
					zahteve_crke.put(crke[index], triple_crke);
				}
			}
			
			@SuppressWarnings("unchecked")
			LinkedList<String> zadoscajo_clone = (LinkedList<String>) zadoscajo.clone();
			for (String beseda : zadoscajo_clone) {
				boolean ustreza = true;
				
				
				for (String crka : zahteve_crke.keySet()) {
					
					//1 zahteva: fiksna pozicija crke
					for (int k : zahteve_crke.get(crka).getFirst()) {
						String crka_of_char = "";
						crka_of_char += beseda.charAt(k);
						if (crka_of_char != crka) {
							ustreza = false;
						}
					}
					
					//2 zahteva: stevilo ponovitev te crke
					int p = stevilo_ponovitev(beseda.split(""), crka);
					if (!(zahteve_crke.get(crka).getThird().contains(p))) {
						ustreza = false;
					}
					//3 zahteva: crka je lahko le na nekaterih mestih
					LinkedList<Integer> indeksi = ponovitve(beseda.split(""), crka);
					for (int poz : indeksi) {
						if (!(zahteve_crke.get(crka).getSecond().contains(poz))) {
							ustreza = false;
						}
					}
				}
				if (!ustreza) {
					zadoscajo.remove(beseda);
				}
			}
		return zadoscajo.size();
	}
	//pomozne funkcije za stevilo_moznih...
	public static LinkedList<Integer> ponovitve(String[] str_lst, String str) {
		LinkedList<Integer> indexi = new LinkedList<Integer>();
		for (int l = 0; l < str_lst.length; l++) {
			if (str_lst[l] == str) {
				indexi.add(l);
			}
		}
		return indexi;
	}
	
	public static int stevilo_ponovitev(String[] str_lst, String str) {
		return ponovitve(str_lst, str).size();
	}

	public void posodobi(String poteza) {
		if (stanje.plosca1 == StanjeEnum.V_TEKU) {
			int i1 = 0;
			while (plosca1_barve[i1][0] != Polje.PRAZNO) {
				i1++;
				if (i1 == 6) break;
			}
			if (i1 > 0) {
				Polje[] test1 = new Polje[5];
				for (int k1 = 0; k1 < test1.length; k1++) {
					test1[k1] = Polje.PRAVILNO;
				}
				if (Arrays.equals(test1, plosca1_barve[i1 - 1])) {
					stanje.plosca1 = StanjeEnum.ZMAGA;
					}
				else if (i1 == 6) {
					stanje.plosca1 = StanjeEnum.PORAZ;
					stanje.stevilo_moznosti1 = stevilo_moznih(poteza, 1);
				}
				else {
					stanje.stevilo_moznosti1 = stevilo_moznih(poteza, 1);
				}
			}
		}
		if (stanje.plosca2 == StanjeEnum.V_TEKU) {
			int i2 = 0;
			while (plosca2_barve[i2][0] != Polje.PRAZNO) {
				i2++;
				if (i2 == 6) break;
			}
			if (i2 > 0) {
				Polje[] test2 = new Polje[5];
				for (int k2 = 0; k2 < test2.length; k2++) {
					test2[k2] = Polje.PRAVILNO;
				}
				if (Arrays.equals(test2, plosca2_barve[i2 - 1])) {
					stanje.plosca2 = StanjeEnum.ZMAGA;
				}
				else if (i2 == 6) {
					stanje.plosca2 = StanjeEnum.PORAZ;
					stanje.stevilo_moznosti2 = stevilo_moznih(poteza, 2);
					}
				else {
					stanje.stevilo_moznosti2 = stevilo_moznih(poteza, 2);
				}
			}
		}
	}
	
	public void posodobi_in_odigraj(String poteza) {
		odigraj(poteza);
		posodobi(poteza);
	}
	
	public StanjeEnum stanje_celota() {
		if ((stanje.plosca1 == StanjeEnum.PORAZ) || (stanje.plosca2 == StanjeEnum.PORAZ)) {
			return StanjeEnum.PORAZ;
		}
		else if ((stanje.plosca1 == StanjeEnum.ZMAGA) && (stanje.plosca2 == StanjeEnum.ZMAGA)) {
			return StanjeEnum.ZMAGA;
		}
		else {
			return StanjeEnum.V_TEKU;
		}
	}
	// v vodji: preveri, da je dovolj dolga beseda, so znaki ustrezni

}
