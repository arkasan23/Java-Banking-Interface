package banking;

public class PassTimeValidator {

	private int amount;

	public PassTimeValidator() {
		// bank is not needed for the validation of pass time
	}

	private void processCommandString(String[] commandParsed) {
		try {
			amount = Integer.parseInt(commandParsed[1]);
		} catch (Exception e) {
			amount = -1;
		}
	}

	public boolean validateAmount() {
		return amount >= 1 && amount <= 60;
	}

	public boolean isCommandValid(String[] commandParsed) {
		processCommandString(commandParsed);

		if (commandParsed.length != 2) {
			return false;
		}

		return this.validateAmount();
	}
}
