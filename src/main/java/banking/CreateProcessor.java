package banking;

public class CreateProcessor {

	private String accountType;
	private int accountID;
	private double APR;
	private double balance;
	private Bank bank;

	public CreateProcessor(Bank bank) {
		this.bank = bank;
	}

	public void processCommand(String[] commandParsed) {

		accountType = commandParsed[1];
		accountID = Integer.parseInt(commandParsed[2]);
		APR = Double.parseDouble(commandParsed[3]);

		if (commandParsed.length > 4) {
			balance = Double.parseDouble(commandParsed[4]);
		}

		if (accountType.equals("checking")) {
			Checking checking = new Checking(APR, accountID);
			bank.addAccount(checking);
		} else if (accountType.equals("savings")) {
			Saving saving = new Saving(APR, accountID);
			bank.addAccount(saving);
		} else if (accountType.equals("cd")) {
			CD cd = new CD(APR, balance, accountID);
			bank.addAccount(cd);
		}
	}
}
