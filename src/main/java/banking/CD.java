package banking;

public class CD extends Account {

	public CD(double apr, double balance, int id) {
		super(apr, id);
		super.setBalance(balance);
		super.setAccountType("Cd");
	}

}
