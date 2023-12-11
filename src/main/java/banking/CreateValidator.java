package banking;

public class CreateValidator {

	private String accountType;
	private int accountID;
	private double APR;
	private double balance;
	private Bank bank;
	private String[] commandParsed;

	public CreateValidator(Bank bank) {
		this.bank = bank;
	}

	private boolean checkAPR() {
		if (APR > 10 || APR < 0) {
			return false;
		}

		return true;
	}

	private boolean checkAccountId() {
		if (String.valueOf(accountID).length() != 8) {
			return false;
		}

		if (bank.getAccounts().containsKey(accountID)) {
			return false;
		}

		return true;
	}

	private boolean checkBalance() {
		if (balance == -1) {
			return false;
		}

		if (balance < 1000 || balance > 10000) {
			return false;
		}
		return true;
	}

	private void processCommand(String[] commandParsed) {
		this.commandParsed = commandParsed;

		try {
			accountType = commandParsed[1];
		} catch (Exception e) {
			accountType = null;
		}
		try {
			accountID = Integer.parseInt(commandParsed[2]);
		} catch (Exception e) {
			accountID = -1;
		}
		try {
			APR = Double.parseDouble(commandParsed[3]);
		} catch (Exception e) {
			APR = -1;
		}
		try {
			balance = Double.parseDouble(commandParsed[4]);
		} catch (Exception e) {
			balance = -1;
		}
	}

	private boolean validateCheckingOrSaving() {
		if (commandParsed.length > 4) {
			return false;
		}

		if (this.checkAPR() && this.checkAccountId()) {
			return true;
		} else {
			return false;
		}
	}

	private boolean validateCD() {
		if (commandParsed.length > 5) {
			return false;
		}

		if (this.checkAPR() && this.checkBalance() && this.checkAccountId()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isCommandValid(String[] commandParsed) {
		processCommand(commandParsed);

		if (commandParsed.length < 4) {
			return false;
		}

		if (accountType.equals("checking") || accountType.equals("savings")) {
			return validateCheckingOrSaving();
		} else if (accountType.equals("cd")) {
			return validateCD();
		}
		return false;
	}
}
