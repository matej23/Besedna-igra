package logika;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;



public class Igra {
	
	
	public static final int V = 6;
	public static final int S = 5;
	
	public static LinkedList<String> LST; 
	
	public String[][] plosca1_vrednosti;
	public String[][] plosca2_vrednosti;
	public Polje[][] plosca1_barve;
	public Polje[][] plosca2_barve;
	
	public Stanje stanje;
	
	public String beseda1;
	public String beseda2;
	
	public BarveCrke barveCrke;
	
	public LinkedList<Integer> steviloBesed1;
	public LinkedList<Integer> steviloBesed2;
	
	public static LinkedList<String> mozneBesede1;
	public static LinkedList<String> mozneBesede2;
	
	public Jezik jezik;
	protected ColorTheme color;
	protected Cas cas;
	
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
	
	// pomozna funkcija za steviloMoznih 
//	public static boolean seUjema(String element, String poskus, String geslo) {
//		Map<Integer, Polje> slovar = barvaZaVrednost(geslo, poskus);
//		String[] lst_poskus = poskus.split("");
//		String zeleni = "";
//		String rumeni = "";
//		String vzorec = "";
//		LinkedList<String> zeleniSez = new LinkedList<String>();
//		LinkedList<String> siviSez = new LinkedList<String>();
//		LinkedList<String> rumeniSez = new LinkedList<String>();
//		for (int i = 0; i < poskus.length(); ++i) {
//			if (slovar.get(i) == Polje.PRAVILNO) {
//				zeleni += lst_poskus[i];
//				zeleniSez.add(lst_poskus[i]);
//			}
//			else if (slovar.get(i) == Polje.DELNOPRAVILNO) {
//				zeleni += ".";
//				rumeni += "(?=.*"+lst_poskus[i]+".*)";
//				rumeniSez.add(lst_poskus[i]);
//			}
//			else {
//				zeleni += ".";
//				siviSez.add(lst_poskus[i]);
//			}
//		}
//		siviSez = precisciSivi(siviSez, zeleniSez);
//		String sivi = pretvoriVNiz (precisciSivi(siviSez, rumeniSez));
//		if ((!rumeni.equals("")) || (!sivi.equals(""))) vzorec += rumeni + "(?=[a-zčšž]{5})";
//		else vzorec += "([a-zčšž]{5})";
//		for (int i = 0; i < poskus.length(); ++i) {
//			String pomozni = "";
//			if (!sivi.equals("")) {
//				pomozni += "[^" + sivi;
//				if (slovar.get(i) == Polje.DELNOPRAVILNO) {
//				pomozni += lst_poskus[i];
//				}
//				pomozni += "]";
//			}
//			vzorec += pomozni;
//		}
//		vzorec += "$";
//
//		return Pattern.matches(vzorec, element) && Pattern.matches(zeleni, element);
//	}
//	
//	public static LinkedList<String> precisciSivi (LinkedList<String> sivi, LinkedList<String> rumeni) {
//		LinkedList<String> preciscen = new LinkedList<String>();
//		for (String niz : sivi) {
//			if (!rumeni.contains(niz)) {
//				preciscen.add(niz);
//			}
//		}
//		return preciscen;
//	}
//	
//	public static String pretvoriVNiz(LinkedList<String> seznam) {
//		return String.join("", seznam);
//	}
//	
//	// izracuna stevilo moznih besed glede na obarvanost polj na plosci
//	
//	public int steviloMoznih(String poskus, int steviloPlosca) {
//		if (steviloPlosca == 1) {
//			for (String element : LST) {
//				if (!seUjema(element, poskus, beseda1)) {
//					mozneBesede1.remove(element);
//				}
//			}
//			return mozneBesede1.size();
//		}
//		else {
//			for (String element : LST) {
//				if (!seUjema(element, poskus, beseda2)) {
//					mozneBesede2.remove(element);
//				}
//			}
//			return mozneBesede2.size();
//		}
//	}

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
					if (plosca1_barve[i1-1][k1] == Polje.PRAVILNO) {
						barveCrke.barve1.put(plosca1_vrednosti[i1-1][k1], Polje.PRAVILNO);
					}
					else if (plosca1_barve[i1-1][k1] == Polje.NAPACNO) {
						if (barveCrke.barve1.get(plosca1_vrednosti[i1-1][k1]) == Polje.PRAZNO) {
							barveCrke.barve1.put(plosca1_vrednosti[i1-1][k1], Polje.NAPACNO);
						}
					}
					else if (plosca1_barve[i1-1][k1] == Polje.DELNOPRAVILNO) {
						if (barveCrke.barve1.get(plosca1_vrednosti[i1-1][k1]) == Polje.PRAZNO) {
							barveCrke.barve1.put(plosca1_vrednosti[i1-1][k1], Polje.DELNOPRAVILNO);
						}
					}
				if (Arrays.equals(test1, plosca1_barve[i1 - 1])) {
					stanje.plosca1 = StanjeEnum.ZMAGA;
					}
				else if (i1 == 6) {
					stanje.plosca1 = StanjeEnum.PORAZ;
					}	
			}
//			stanje.stevilo_moznosti1 = steviloMoznih(poteza, 1);
//			steviloBesed1.addLast(steviloMoznih(poteza, 1));
//			posodobiCrke(barveCrke.barve1, 1);
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
					if (plosca2_barve[i2-1][k2] == Polje.PRAVILNO) {
						barveCrke.barve2.put(plosca1_vrednosti[i2-1][k2], Polje.PRAVILNO);
					}
					else if (plosca2_barve[i2-1][k2] == Polje.NAPACNO) {
						if (barveCrke.barve2.get(plosca1_vrednosti[i2-1][k2]) == Polje.PRAZNO) {
							barveCrke.barve2.put(plosca1_vrednosti[i2-1][k2], Polje.NAPACNO);
						}
					}
					else if (plosca1_barve[i2-1][k2] == Polje.DELNOPRAVILNO) {
						if (barveCrke.barve2.get(plosca1_vrednosti[i2-1][k2]) == Polje.PRAZNO) {
							barveCrke.barve2.put(plosca1_vrednosti[i2-1][k2], Polje.DELNOPRAVILNO);
						}
					}
				}
				if (Arrays.equals(test2, plosca2_barve[i2 - 1])) {
					stanje.plosca2 = StanjeEnum.ZMAGA;
				}
				else if (i2 == 6) {
					stanje.plosca2 = StanjeEnum.PORAZ;
					}
			}
//			stanje.stevilo_moznosti2 = steviloMoznih(poteza, 2);
//			steviloBesed2.addLast(steviloMoznih(poteza,2));
//			posodobiCrke(barveCrke.barve2, 2);
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

	public static Igra novaIgra (Jezik jezik) {
		Igra igra = new Igra(jezik);
		return igra;
	}
	
}


