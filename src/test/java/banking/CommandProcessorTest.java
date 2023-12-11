package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {

	CommandProcessor commandProcessor;
	Bank bank;
	Checking checking;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		checking = new Checking(5.0, 12345678);
		bank.addAccount(checking);
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	public void create_command() {
		commandProcessor.processCommand("Create savings 12345679 0.01");
		Account account = bank.retrieveAccount(12345679);
		assertEquals(bank.getAccounts().size(), 2);
		assertTrue(account.getId() == 12345679 && account.getApr() == 0.01 && account.getAccountType() == "Savings");
	}

	@Test
	public void deposit_command() {
		commandProcessor.processCommand("deposit 12345678 100");
		double actual = checking.getBalance();
		assertEquals(actual, 100);
	}

}
