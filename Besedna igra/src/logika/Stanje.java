package logika;

public class Stanje {
	public StanjeEnum plosca1;
	public StanjeEnum plosca2;
	public int stevilo_moznosti1;
	public int stevilo_moznosti2;

	public Stanje(int stevilo_moznosti) {
		//stevilo moznosti je v resnici dolzina seznam, ki je dobljena na podlagi izbranega jezika
		this.plosca1 = StanjeEnum.V_TEKU;
		this.plosca2 = StanjeEnum.V_TEKU;
		this.stevilo_moznosti1 = stevilo_moznosti;
		this.stevilo_moznosti2 = stevilo_moznosti;
	}
}
