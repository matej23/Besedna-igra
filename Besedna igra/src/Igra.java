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
	
	protected String beseda1;
	protected String beseda2;
	protected Jezik jezik;
	
	public Igra(Jezik jezik) {
		this.jezik = jezik;
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
	
	public static Map<String, Polje> barva_za_vrednost(String geslo, String poskus) {
		Map<String, Polje> slovar = new HashMap<String, Polje>();
		String[] lst_poskus = poskus.split("");
		String[] lst_geslo = geslo.split("");
		for (int i = 0; i < poskus.length(); ++i) {
			// todo :)
		}
	}
	
	// funkcija, ki updejta vse štiri plošče glede na novo besedo 
	// (preveri, ali sta obe plošči v teku), updejtamo stanje
	
	// funkcija, ki preračuna število možnih besed
	
	// "importava" seznam besed 
	
	// preveri, da je beseda v sez 

	// v vodji: preveri, da je dovolj dolga beseda, so znaki ustrezni
	
	// čas
	
	
	
}
