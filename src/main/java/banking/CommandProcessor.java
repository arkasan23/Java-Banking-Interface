package banking;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommandProcessor {

	private String commandType;
	private String[] commandParsed;
	private Bank bank;

	private CreateProcessor createProcessor;
	private DepositProcessor depositProcessor;
	private WithdrawProcessor withdrawProcessor;
	private PassTimeProcessor passTimeProcessor;
	private TransferProcessor transferProcessor;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
		this.createProcessor = new CreateProcessor(bank);
		this.depositProcessor = new DepositProcessor(bank);
		this.withdrawProcessor = new WithdrawProcessor(bank);
		this.passTimeProcessor = new PassTimeProcessor(bank);
		this.transferProcessor = new TransferProcessor(bank);
	}

	private void switchStatement(String commandType, String command) {
		switch (commandType) {
		case "create":
			createProcessor.processCommand(commandParsed);
			break;
		case "deposit":
			depositProcessor.processCommand(commandParsed, command);
			break;
		case "pass":
			passTimeProcessor.processCommand(commandParsed);
			break;
		case "withdraw":
			withdrawProcessor.processCommand(commandParsed, command);
			break;
		case "transfer":
			transferProcessor.processCommand(commandParsed, command);
			break;
		default:
			break;
		}
	}

	public void processCommand(String command) {

		String[] arguments = command.split(" ");

		for (int i = 0; i < arguments.length; i++) {
			arguments[i] = arguments[i].toLowerCase();
		}
		commandType = arguments[0];
		commandParsed = arguments;

		switchStatement(commandType, command);

	}

	public List<String> getStateOfAccountsAndTransactionHistory() {
		List<String> commands = new ArrayList<>();

		Iterator<Account> it = bank.getAccounts().values().iterator();
		while (it.hasNext()) {
			Account account = it.next();
			commands.add(account.getAccountState());
			commands.addAll(account.getTransationHistory());
		}

		return commands;

	}

}
