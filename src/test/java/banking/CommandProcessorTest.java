package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {

	CommandProcessor commandProcessor;
	Bank bank;
	Checking checking;
	Saving saving;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		checking = new Checking(5.0, 12345678);
		saving = new Saving(5.0, 87654321);
		bank.addAccount(checking);
		bank.addAccount(saving);
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	public void create_command() {
		commandProcessor.processCommand("Create savings 12345679 0.01");
		Account account = bank.retrieveAccount(12345679);
		assertEquals(bank.getAccounts().size(), 3);
		assertTrue(account.getId() == 12345679 && account.getApr() == 0.01 && account.getAccountType() == "Savings");
	}

	@Test
	public void deposit_command() {
		commandProcessor.processCommand("deposit 12345678 100");
		double actual = checking.getBalance();
		assertEquals(actual, 100);
	}

	@Test
	public void withdraw_command() {
		checking.deposit(500);
		commandProcessor.processCommand("withdraw 12345678 250");
		double actual = checking.getBalance();
		assertEquals(actual, 250);
	}

	@Test
	public void transfer_command() {
		checking.deposit(500);
		commandProcessor.processCommand("transfer 12345678 87654321 250");
		double actual = saving.getBalance();
		assertEquals(actual, 250);
	}

	@Test
	public void pass_time() {
		saving.deposit(250);
		commandProcessor.processCommand("pass 1");
		double actual = saving.getBalance();
		assertEquals(251.04166666666666, actual);
	}

}
