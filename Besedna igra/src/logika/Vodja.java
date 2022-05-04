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
				Igra igra = new Igra();
				
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
						if (!igra.LST.contains(poskus)) {
							System.out.println("POSKUS NI VELJAVEN ");
						}
						else {
							igra.posodobi_in_odigraj(poskus);
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
	
}
