package banking;

public class DepositProcessor {

	private int accountID;
	private double amount;
	private Bank bank;

	public DepositProcessor(Bank bank) {
		this.bank = bank;
	}

	public void processCommand(String[] commandParsed, String command) {

		accountID = Integer.parseInt(commandParsed[1]);
		amount = Double.parseDouble(commandParsed[2]);

		bank.getAccounts().get(accountID).addCommand(command);
		bank.depositByID(accountID, amount);
	}

}
