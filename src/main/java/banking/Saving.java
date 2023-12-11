package banking;

public class Saving extends Account {

	public Saving(double apr, int id) {
		super(apr, id);
		super.setAccountType("Savings");
	}

}
