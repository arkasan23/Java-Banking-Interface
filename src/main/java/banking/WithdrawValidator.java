package banking;

public class WithdrawValidator {

	private int accountID;
	private double amount;

	private Bank bank;

	public WithdrawValidator(Bank bank) {
		this.bank = bank;
	}

	private void processCommandString(String[] commandParsed) {

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

	private boolean validateSaving(Account account) {
		if (amount > 1000 || account.getWithDrawnThisMonth() == true) {
			return false;
		} else {
			return true;
		}

	}

	private boolean validateCD(Account account) {
		if (account.getMonthsSinceCreation() < 12 || amount < account.getBalance()) {
			return false;
		} else {
			return true;
		}
	}

	private boolean validateChecking() {
		if (amount > 400) {
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
			return validateSaving(account);
		} else if (account.getAccountType().equals("Checking")) {
			return validateChecking();
		} else if (account.getAccountType().equals("Cd")) {
			return validateCD(account);
		}
		return false;
	}

	public boolean isCommandValid(String[] commandParsed) {
		processCommandString(commandParsed);

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
