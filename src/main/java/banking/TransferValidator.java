package banking;

public class TransferValidator {

	private int fromId;
	private int toId;
	private double amount;
	private DepositValidator deposit;
	private WithdrawValidator withdraw;

	public TransferValidator(Bank bank) {
		this.deposit = new DepositValidator(bank);
		this.withdraw = new WithdrawValidator(bank);
	}

	private void processCommandString(String[] commandParsed) {
		try {
			fromId = Integer.parseInt(commandParsed[1]);
		} catch (Exception e) {
			fromId = -1;
		}

		try {
			toId = Integer.parseInt(commandParsed[2]);
		} catch (Exception e) {
			toId = -1;
		}

		try {
			amount = Double.parseDouble(commandParsed[3]);
		} catch (Exception e) {
			amount = -1;
		}

	}

	public boolean isCommandValid(String[] commandParsed) {
		processCommandString(commandParsed);

		if (amount == -1 || fromId == -1 || toId == -1 || commandParsed.length != 4 || fromId == toId) {
			return false;
		}

		String[] depositCommand = { "deposit", Integer.toString(toId), Double.toString(amount) };
		String[] withdrawCommand = { "withdraw", Integer.toString(fromId), Double.toString(amount) };
		if (!deposit.isCommandValid(depositCommand) || !withdraw.isCommandValid(withdrawCommand)) {
			return false;
		}

		return true;

	}

}
