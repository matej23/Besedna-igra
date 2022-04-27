import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;


public class Igra {
	
	
	public static final int V = 6;
	public static final int S = 5;
	
	public static LinkedList<String> LST; 
	static {
		try {
			LST = seznam_besed("src/besede.txt");
		}
		catch (IOException ex){
			System.out.println("Datoteke besede.txt ne najde");
		}
	}
	
	public String[][] plosca1_vrednosti;
	public String[][] plosca2_vrednosti;
	public Polje[][] plosca1_barve;
	public Polje[][] plosca2_barve;
	
	protected Stanje stanje;
	protected String beseda1;
	protected String beseda2;
	protected Jezik jezik;
	
	public Igra(Jezik jezik) {
		this.jezik = jezik;
		
		stanje = Stanje.V_TEKU_OBE;
		Random rand = new Random();
		int i = rand.nextInt(LST.size());
		beseda1 = LST.get(i);
		
		LinkedList<String> lst = LST;
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
			String vrstica = vhod.readLine().trim();
			sez.add(vrstica);
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
	// (preveri, ali sta obe plosci v teku), updejtamo stanje
	public static void odigraj(String poskus, Igra igra) {
		String[] lst_poskus = poskus.split("");
		if (igra.stanje == Stanje.V_TEKU_OBE){
			int vrstica = 0;
			while (igra.plosca1_barve[vrstica][0] != Polje.PRAZNO) {
				vrstica++;
			}
			Map<Integer, Polje> barve_vrednosti1 = barva_za_vrednost(igra.beseda1, poskus);
			Map<Integer, Polje> barve_vrednosti2 = barva_za_vrednost(igra.beseda2, poskus);
			
			for (int i = 0; i < igra.plosca1_barve.length; i++ ) {
				igra.plosca1_barve[vrstica][i] = barve_vrednosti1.get(i);
				igra.plosca2_barve[vrstica][i] = barve_vrednosti2.get(i);
				igra.plosca1_vrednosti[vrstica][i] = lst_poskus[i];
				igra.plosca2_vrednosti[vrstica][i] = lst_poskus[i];		
			}
		}
		
		else if (igra.stanje == Stanje.V_TEKU_1) {
			int vrstica = 0;
			while (igra.plosca1_barve[vrstica][0] != Polje.PRAZNO) {
				vrstica++;
			}
			Map<Integer, Polje> barve_vrednosti1 = barva_za_vrednost(igra.beseda1, poskus);
			
			for (int i = 0; i < igra.plosca1_barve.length; i++ ) {
				igra.plosca1_barve[vrstica][i] = barve_vrednosti1.get(i);
				igra.plosca1_vrednosti[vrstica][i] = lst_poskus[i];
			}
		}
		
		else if (igra.stanje == Stanje.V_TEKU_2) {
			int vrstica = 0;
			while (igra.plosca2_barve[vrstica][0] != Polje.PRAZNO) {
				vrstica++;
			}
			Map<Integer, Polje> barve_vrednosti2 = barva_za_vrednost(igra.beseda2, poskus);
			
			for (int i = 0; i < igra.plosca2_barve.length; i++ ) {
				igra.plosca2_barve[vrstica][i] = barve_vrednosti2.get(i);
				igra.plosca2_vrednosti[vrstica][i] = lst_poskus[i];		
			}
		}
		else {
			System.out.print("STANJE ZA VNOS POTEZE NI USTREZNO ! :(");
		}
		//spremeniva tu stanje/ga preveriva/zamenjava - 
		//ali se samo skliceva na class Stanje ? - pa lahko tu dodava se moznosti...
	}
	
	// funkcija, ki preracuna stevilo moznih besed
	// ne ustrasi se :) triple je samo vnos (a, b, c) v javo, ker je to tu elegantno
	// no vsaj jst tako menim :)
	public static int stevilo_moznih(int st_plosca, Igra igra) {
		LinkedList<String> preostale = Igra.LST;
		String geslo;
		String[][] plosca;
		Polje[][] barve;
		
		if (st_plosca == 1) {
			geslo = igra.beseda1;
			plosca = igra.plosca1_vrednosti;
			barve = igra.plosca1_barve;
		}
		else if (st_plosca == 2) {
			geslo = igra.beseda2;
			plosca = igra.plosca2_vrednosti;
			barve = igra.plosca2_barve;
		}
		else {
			System.out.print("TA PLOSCA NE OBSTAJA");
			return 0; 
			//samo nekaj da lahko spodaj vedno gledamo variable plosco... 
			//do tega tako ali tako ne bo prihajalo.. hopefully :)
		}
		
		for (int i = 0; i < plosca.length; i++) {
			String poskus = "";
			String[] poskus_crke = plosca[i];
			
			Map<String, Triple<LinkedList<Integer>, LinkedList<Integer>, LinkedList<Integer>>> zahteve_crke = new HashMap<String, Triple<LinkedList<Integer>, LinkedList<Integer>, LinkedList<Integer>>>();
			
			for (int j = 0; j < poskus_crke.length; j++) {
				poskus += plosca[i][j];
				}
		
			Map<String, Integer> ponovitve = new HashMap<String, Integer>();
 			for (int k = 0; k < poskus_crke.length; k++) {
				ponovitve.put(poskus_crke[k], stevilo_ponovitev(poskus_crke, poskus_crke[k]));
				}
			
			for (String crka : ponovitve.keySet()) {
				if (ponovitve.get(crka) == 1) {
					int index = poskus.indexOf(crka);
					
					if (barve[i][index] == Polje.PRAVILNO) {
						LinkedList<Integer> potrebna = new LinkedList<Integer>(Arrays.asList(index));
						LinkedList<Integer> mozna = new LinkedList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5));
						LinkedList<Integer> stevilo = new LinkedList<Integer>(Arrays.asList(1, 2, 3, 4, 5));
						Triple<LinkedList<Integer>,LinkedList<Integer>, LinkedList<Integer>> triple_crke= new Triple<>(potrebna, mozna, stevilo);
						zahteve_crke.put(crka, triple_crke);
					}
					
					else if (barve[i][index] == Polje.DELNOPRAVILNO) {
						LinkedList<Integer> potrebna = new LinkedList<Integer>();
						LinkedList<Integer> mozna = new LinkedList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5));
						mozna.remove(index);
						LinkedList<Integer> stevilo = new LinkedList<Integer>(Arrays.asList(1, 2, 3, 4, 5));
						Triple<LinkedList<Integer>,LinkedList<Integer>, LinkedList<Integer>> triple_crke= new Triple<>(potrebna, mozna, stevilo);
						zahteve_crke.put(crka, triple_crke);
					}
					
					else {
						LinkedList<Integer> potrebna = new LinkedList<Integer>();
						LinkedList<Integer> mozna = new LinkedList<Integer>();
						LinkedList<Integer> stevilo = new LinkedList<Integer>(Arrays.asList(1, 2, 3, 4, 5));
						Triple<LinkedList<Integer>,LinkedList<Integer>, LinkedList<Integer>> triple_crke= new Triple<>(potrebna, mozna, stevilo);
						zahteve_crke.put(crka, triple_crke);
					}
				}
				
				else {
					LinkedList<Integer> indexi = ponovitve(poskus_crke, crka);
					LinkedList<Integer> ostali_mozni = new LinkedList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5));
					LinkedList<Integer> potrebni = new LinkedList<Integer>();
					
					int niso_sivi = indexi.size();
					for (int n = 0; n < indexi.size(); n++) {
						if (barve[i][indexi.get(n)] == Polje.NAPACNO) {
							ostali_mozni.remove(indexi.get(n));
							niso_sivi--;
						}
				
						else if (barve[i][indexi.get(n)] == Polje.DELNOPRAVILNO) {
							ostali_mozni.remove(indexi.get(n));
						}
						
						else {
							potrebni.add(indexi.get(n));
						}
					}
					
					LinkedList<Integer> stevilo = new LinkedList<Integer>(Arrays.asList(niso_sivi));
					Triple<LinkedList<Integer>,LinkedList<Integer>, LinkedList<Integer>> triple_crke= new Triple<>(potrebni, ostali_mozni, stevilo);
					zahteve_crke.put(crka, triple_crke);
				}
			}
			// tu je za vsak element zahteve_crke treba iz LinkedList odtraniti tiste, ki ne zadoscajo
		}
		return preostale.size();
	}
	
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

	public static boolean ali_je_ustrezna_beseda(String poskus) {
		for (String beseda : LST) {
			if (poskus.equals(beseda)) return true;
		}
		return false;
	}

	// v vodji: preveri, da je dovolj dolga beseda, so znaki ustrezni
	
	// cas
	
}
