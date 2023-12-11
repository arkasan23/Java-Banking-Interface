package banking;

public class DepositValidator {

	private int accountID;
	private double amount;
	private String[] commandParsed;

	private Bank bank;

	public DepositValidator(Bank bank) {
		this.bank = bank;

	}

	private boolean validateAccountId() {
		if (String.valueOf(accountID).length() != 8) {
			return false;
		}

		if (bank.getAccounts().containsKey(accountID)) {
			return true;
		} else {
			return false;
		}

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

	private boolean validateMisc() {
		if (commandParsed.length != 3) {
			return false;
		}

		if (this.validateAccountId() && this.validateAmount()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isCommandValid(String[] commandParsed) {
		processCommand(commandParsed);
		this.commandParsed = commandParsed;

		return validateMisc();
	}
}
