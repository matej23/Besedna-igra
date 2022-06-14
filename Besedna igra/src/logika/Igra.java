package logika;
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
	
	public String[][] plosca1_vrednosti;
	public String[][] plosca2_vrednosti;
	public Polje[][] plosca1_barve;
	public Polje[][] plosca2_barve;
	
	public static Stanje stanje;
	
	public String beseda1;
	public String beseda2;
	
	public BarveCrke barveCrke;
	
	public LinkedList<Integer> steviloBesed1;
	public LinkedList<Integer> steviloBesed2;
	
	public static LinkedList<String> mozneBesede1;
	public static LinkedList<String> mozneBesede2;
	
	public Jezik jezik;
	
	//Jezik jezik
	@SuppressWarnings("unchecked")
	public Igra(Jezik jezik) {
		this.jezik = jezik;
		barveCrke = new BarveCrke(jezik);
		
		if (jezik == Jezik.ANG) {
			{
				try {
					LST = seznamBesed("besede_ang.txt");
				}
				catch (IOException ex){
					System.out.println("Datoteke besede.txt ne najde");
				}
			}
		}
		else {
			{
				try {
					LST = seznamBesed("besede_slo.txt");
				}
				catch (IOException ex){
					System.out.println("Datoteke besede.txt ne najde");
				}
			}
		}
		mozneBesede1 = (LinkedList<String>) LST.clone();
		mozneBesede2 = (LinkedList<String>) LST.clone();
		
		steviloBesed1 = new LinkedList<Integer>();
		steviloBesed2 = new LinkedList<Integer>();
		

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
	
	public static LinkedList<String> seznamBesed (String imeVhod) throws IOException {
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
	
	public static Map<Integer, Polje> barvaZaVrednost(String geslo, String poskus) {
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
	
	public void odigraj(String poskus) {
		String[] lst_poskus = poskus.split("");
		if ((stanje.plosca1 == StanjeEnum.V_TEKU) && (stanje.plosca2 == StanjeEnum.V_TEKU)){
			int vrstica = 0;
			while (plosca1_barve[vrstica][0] != Polje.PRAZNO) {
				vrstica++;
			}
			Map<Integer, Polje> barve_vrednosti1 = barvaZaVrednost(beseda1, poskus);
			Map<Integer, Polje> barve_vrednosti2 = barvaZaVrednost(beseda2, poskus);
			
			for (int i = 0; i < plosca1_barve[0].length; i++ ) {
				plosca1_barve[vrstica][i] = barve_vrednosti1.get(i);
				plosca2_barve[vrstica][i] = barve_vrednosti2.get(i);
				plosca1_vrednosti[vrstica][i] = lst_poskus[i].toUpperCase();
				plosca2_vrednosti[vrstica][i] = lst_poskus[i].toUpperCase();		
			}
		}
		
		else if (stanje.plosca1 == StanjeEnum.V_TEKU) {
			int vrstica = 0;
			while (plosca1_barve[vrstica][0] != Polje.PRAZNO) {
				vrstica++;
			}
			Map<Integer, Polje> barve_vrednosti1 = barvaZaVrednost(beseda1, poskus);
			
			for (int i = 0; i < plosca1_barve[0].length; i++ ) {
				plosca1_barve[vrstica][i] = barve_vrednosti1.get(i);
				plosca1_vrednosti[vrstica][i] = lst_poskus[i].toUpperCase();
			}
		}
		
		else if (stanje.plosca2 == StanjeEnum.V_TEKU) {
			int vrstica = 0;
			while (plosca2_barve[vrstica][0] != Polje.PRAZNO) {
				vrstica++;
			}
			Map<Integer, Polje> barve_vrednosti2 = barvaZaVrednost(beseda2, poskus);
			
			for (int i = 0; i < plosca2_barve[0].length; i++ ) {
				plosca2_barve[vrstica][i] = barve_vrednosti2.get(i);
				plosca2_vrednosti[vrstica][i] = lst_poskus[i].toUpperCase();		
			}
		}
		else {
			System.out.print("STANJE ZA VNOS POTEZE NI USTREZNO ! :(");
		}
	}
	
	
	
	public void posodobi(String poteza, int plosca) {
		StanjeEnum stanjePlosca;
		Polje [][] ploscaBarve;
		HashMap<String, Polje> barve;
		String [][] ploscaVrednosti;
		
		if (plosca == 1) {
			stanjePlosca = stanje.plosca1;
			ploscaBarve = plosca1_barve;
			barve = barveCrke.barve1;
			ploscaVrednosti = plosca1_vrednosti;
		}
		else {
			stanjePlosca = stanje.plosca2;
			ploscaBarve = plosca2_barve;
			barve = barveCrke.barve2;
			ploscaVrednosti = plosca2_vrednosti;
		}
		
		if (stanjePlosca == StanjeEnum.V_TEKU) {
			int i = 0;
			while (ploscaBarve[i][0] != Polje.PRAZNO) {
				i++;
				if (i == 6) break;
			}
			
			if (i > 0) {
				Polje[] test = new Polje[5];
				
				for (int k = 0; k < test.length; k++) {
					test[k] = Polje.PRAVILNO;
					
					if (ploscaBarve[i-1][k] == Polje.PRAVILNO) {
						barve.put(ploscaVrednosti[i-1][k], Polje.PRAVILNO);
					}
					else if (ploscaBarve[i-1][k] == Polje.NAPACNO) {
						if (barve.get(ploscaVrednosti[i-1][k]) == Polje.PRAZNO) {
							barve.put(ploscaVrednosti[i-1][k], Polje.NAPACNO);
						}
					}
					else if (ploscaBarve[i-1][k] == Polje.DELNOPRAVILNO) {
						if (barve.get(ploscaVrednosti[i-1][k]) == Polje.PRAZNO) {
							barve.put(ploscaVrednosti[i-1][k], Polje.DELNOPRAVILNO);
						}
					}
					
					if (Arrays.equals(test, ploscaBarve[i - 1])) {
						if (plosca == 1) {
							stanje.plosca1 = StanjeEnum.ZMAGA;
						}
						else {
							stanje.plosca2 = StanjeEnum.ZMAGA;
						}
					}
					else if (i == 6) {
						if (plosca == 1) {
							stanje.plosca1 = StanjeEnum.PORAZ;
						}
						else {
							stanje.plosca2 = StanjeEnum.PORAZ;
						}
					}	
				}
			}
		}
	}
	
	
	public void posodobi_in_odigraj(String poteza) {
		odigraj(poteza);
		posodobi(poteza, 1);
		posodobi(poteza, 2);
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

	public static Igra novaIgra (Jezik jezik) {
		Igra igra = new Igra(jezik);
		return igra;
	}
	
}


