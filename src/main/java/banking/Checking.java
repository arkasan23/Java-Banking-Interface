package banking;

public class Checking extends Account {

	public Checking(double apr, int id) {
		super(apr, id);
		super.setAccountType("Checking");
	}

}
