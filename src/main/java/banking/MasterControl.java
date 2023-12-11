package banking;

import java.util.ArrayList;
import java.util.List;

public class MasterControl {

	private CommandValidator commandValidator;
	private CommandProcessor commandProcessor;
	private CommandStorage commandStorage;

	public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor,
			CommandStorage commandStorage) {
		this.commandValidator = commandValidator;
		this.commandProcessor = commandProcessor;
		this.commandStorage = commandStorage;
	}

	public List<String> start(List<String> input) {
		List<String> outputList = new ArrayList<>();

		for (String command : input) {
			if (commandValidator.isCommandValid(command)) {
				commandProcessor.processCommand(command);
			} else {
				commandStorage.addInvalidCommand(command);
			}
		}

		outputList.addAll(commandProcessor.getStateOfAccountsAndTransactionHistory());
		outputList.addAll(commandStorage.getInvalidCommands());

		return outputList;
	}
}
