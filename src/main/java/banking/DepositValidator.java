package banking;

public class DepositValidator {

	private int accountID;
	private double amount;

	private Bank bank;

	public DepositValidator(Bank bank) {
		this.bank = bank;

	}

	private boolean validateAccountId() {
		if (String.valueOf(accountID).length() != 8) {
			return false;
		}

		for (Account account : bank.getAccounts().values()) {
			if (account.getId() == accountID) {
				return true;
			}
		}

		return false;
	}

	private boolean validateSaving() {
		if (amount > 2500) {
			return false;
		} else {
			return true;
		}
	}

	public boolean validateChecking() {
		if (amount > 1000) {
			return false;
		} else {
			return true;
		}
	}

	private boolean validateAmount() {
		if (amount < 0) {
			return false;
		}

		Account account = bank.retrieveAccount(accountID);

		if (account.getAccountType().equals("Savings")) {
			return validateSaving();
		} else if (account.getAccountType().equals("Checking")) {
			return validateChecking();
		} else {
			return false;
		}

	}

	private void processCommand(String[] commandParsed) {

		try {
			accountID = Integer.parseInt(commandParsed[1]);
		} catch (Exception e) {
			accountID = -1;
		}

		try {
			amount = Double.parseDouble(commandParsed[2]);
		} catch (Exception e) {
			amount = -1;
		}

	}

	public boolean isCommandValid(String[] commandParsed) {
		processCommand(commandParsed);

		if (commandParsed.length != 3) {
			return false;
		}

		if (!this.validateAccountId()) {
			return false;
		}
		if (!this.validateAmount()) {
			return false;
		}
		return true;
	}
}
