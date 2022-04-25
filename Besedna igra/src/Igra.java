import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;


public class Igra {
	public static final int V = 6;
	public static final int S = 5;
	
	public static final LinkedList<String> LST = new LinkedList<String>();
	
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
	
	//kaj ce imamo poskus omara - 2 * a ???
	//zdej sem dal na podlagi pozicije - imas lahko razlicno obarvana a-ja
	//se vedno problem... :(
	public static Map<Integer, Polje> barva_za_vrednost(String geslo, String poskus) {
		Map<Integer, Polje> slovar = new HashMap<Integer, Polje>();
		
		String[] lst_poskus = poskus.split("");
		String[] lst_geslo = geslo.split("");
		for (int i = 0; i < poskus.length(); ++i) {
			boolean notri = false;
			for (int j = 0; j < lst_geslo.length; ++j) {
				if (lst_poskus[i]== lst_geslo[j]) {
					notri = true;
				}
			}
			if (lst_poskus[i] == lst_geslo[i]) {
				slovar.put(i, Polje.PRAVILNO);
			}
			else if (notri) {
				slovar.put(i, Polje.DELNOPRAVILNO);
			}
			else {
				slovar.put(i, Polje.NAPACNO);
			}
		}
		return slovar;
	}
	
	// funkcija, ki updejta vse stiri ploskve glede na novo besedo 
	// (preveri, ali sta obe ploskvi v teku), updejtamo stanje
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
	
	// "importava" seznam besed 
	
	// preveri, da je beseda v sez 

	// v vodji: preveri, da je dovolj dolga beseda, so znaki ustrezni
	
	// cas
	
	
	
}
