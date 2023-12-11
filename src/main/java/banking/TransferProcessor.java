package banking;

public class TransferProcessor {

	private Bank bank;
	private double amount;
	private int toID;
	private int fromID;

	public TransferProcessor(Bank bank) {
		this.bank = bank;
	}

	public void processCommand(String[] commandParsed, String command) {

		fromID = Integer.parseInt(commandParsed[1]);
		toID = Integer.parseInt(commandParsed[2]);
		amount = Double.parseDouble(commandParsed[3]);

		bank.getAccounts().get(fromID).addCommand(command);
		bank.getAccounts().get(toID).addCommand(command);

		double startingAmount = bank.getAccounts().get(fromID).getBalance();
		bank.withdrawByID(fromID, amount);

		if (startingAmount < amount) {
			bank.depositByID(toID, startingAmount);
		} else {
			bank.depositByID(toID, amount);
		}
	}
}
