package logika;

public class Stanje {
	protected StanjeEnum plosca1;
	protected StanjeEnum plosca2;
	protected int stevilo_moznosti1;
	protected int stevilo_moznosti2;
	//todo
	//a narediva da bo stevilo besed ze v stanju ? + opis z besedami zgoraj 
	//
	public Stanje(int stevilo_moznosti) {
		//stevilo moznosti je v resnici dolzina seznam, ki je dobljena na podlagi izbranega jezika
		this.plosca1 = StanjeEnum.V_TEKU;
		this.plosca2 = StanjeEnum.V_TEKU;
		this.stevilo_moznosti1 = stevilo_moznosti;
		this.stevilo_moznosti2 = stevilo_moznosti;
	}
}
