package banking;

public class WithdrawProcessor {

	private Bank bank;
	private double amount;
	private int accountID;

	public WithdrawProcessor(Bank bank) {
		this.bank = bank;

	}

	public void processCommand(String[] commandParsed, String command) {

		accountID = Integer.parseInt(commandParsed[1]);
		amount = Double.parseDouble(commandParsed[2]);

		bank.getAccounts().get(accountID).addCommand(command);
		bank.withdrawByID(accountID, amount);
	}

}
