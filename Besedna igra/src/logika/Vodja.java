package logika;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Vodja {
	
	private static BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
	private static BufferedReader p = new BufferedReader(new InputStreamReader(System.in));
	public static void igramo () throws IOException {
		while (true) {
			System.out.println("Nova igra. Prosim, da izberete:");
			System.out.println(" 1 - nova igra");
			System.out.println(" 2 - izhod");

			String s = r.readLine();
			if (s.equals("1")) {
				Igra igra = new Igra(Jezik.ANG);
				
				igranje : while (true) {
					switch (igra.stanje_celota()) {
					case ZMAGA:
						System.out.println("ZMAGALI STE :)");
						break igranje;
					case PORAZ:
						System.out.println("IZGUBILI STE :(");
						break igranje;
					case V_TEKU:
						System.out.println("VNESITE POSKUS:");
						String poskus = p.readLine();
						
						if (!igra.LST.contains(poskus.trim())) {
							System.out.println("POSKUS NI VELJAVEN ");
						}
						else {
							igra.posodobi_in_odigraj(poskus.trim());
							System.out.println(igra.beseda1);
							System.out.println(igra.stanje.plosca1 + "\n");
							System.out.print(igra.stanje.stevilo_moznosti1 + "\n");
							plosca_izpis(igra.plosca1_barve);
							System.out.println("\n");
							System.out.println(igra.beseda2);
							System.out.println(igra.stanje.plosca2+ "\n");
							System.out.print(igra.stanje.stevilo_moznosti2+ "\n");
							plosca_izpis(igra.plosca2_barve);
							//izpisi plosci in rezultate plosc
						}
					}
				}
			}
			else if (s.equals("2")) {
				System.out.println("Nasvidenje!");
				break;
			} 
			else {
				System.out.println("Vnos ni veljaven");
				continue;
			}
		
		}
	}
	
	public static void plosca_izpis(Polje[][] plosca) {
		for (Polje[] polja_i : plosca) {
			for (Polje polja_j : polja_i) {
				if (polja_j == Polje.PRAVILNO) {
					System.out.print("2");
				}
				else if (polja_j == Polje.DELNOPRAVILNO) {
					System.out.print("1");
				}
				else if (polja_j == Polje.NAPACNO) {
					System.out.print("0");
				}
				else {
					System.out.print("_");
				}
			}
			System.out.print("\n");
		}
	}
}
