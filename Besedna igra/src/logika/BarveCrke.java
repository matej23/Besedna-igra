package logika;

import java.util.HashMap;

public class BarveCrke {
	public HashMap<String, Polje> barve1;
	public HashMap<String, Polje> barve2;
	
	public BarveCrke(Jezik jezik) {
		barve1 = new HashMap<String, Polje>();
		barve2 = new HashMap<String, Polje>();
		if (jezik == Jezik.ANG) {
			String [] crkeIzpis = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
			for (String crka : crkeIzpis) {
				barve1.put(crka, Polje.PRAZNO);
				barve2.put(crka, Polje.PRAZNO);
			}
		}
		else {
			String [] crkeIzpis = "ABCČDEFGHIJKLMNOPRSŠTUVZŽ".split("");
			for (String crka : crkeIzpis) {
				barve1.put(crka, Polje.PRAZNO);
				barve2.put(crka, Polje.PRAZNO);
			}
		}
	}
}
