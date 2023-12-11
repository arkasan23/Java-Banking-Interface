package banking;

public class CommandValidator {

	private String commandType;
	private String[] commandParsed;

	private CreateValidator create;
	private DepositValidator deposit;
	private WithdrawValidator withdraw;
	private PassTimeValidator passTime;
	private TransferValidator transfer;

	public CommandValidator(Bank bank) {

		create = new CreateValidator(bank);
		deposit = new DepositValidator(bank);
		withdraw = new WithdrawValidator(bank);
		passTime = new PassTimeValidator();
		transfer = new TransferValidator(bank);
	}

	private boolean switchStatement() {
		switch (commandType) {
		case "create":
			return create.isCommandValid(commandParsed);
		case "deposit":
			return deposit.isCommandValid(commandParsed);
		case "withdraw":
			return withdraw.isCommandValid(commandParsed);
		case "pass":
			return passTime.isCommandValid(commandParsed);
		case "transfer":
			return transfer.isCommandValid(commandParsed);
		default:
			return false;
		}
	}

	public boolean isCommandValid(String command) {

		String[] arguments = command.split(" ");

		for (int i = 0; i < arguments.length; i++) {
			arguments[i] = arguments[i].toLowerCase();
		}
		commandType = arguments[0];
		commandParsed = arguments;

		return switchStatement();

	}

}
