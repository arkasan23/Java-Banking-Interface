package banking;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {

	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
		Checking checking = new Checking(5, 87654321);
		bank.addAccount(checking);
	}

	@Test
	public void account_creation_command() {
		assertTrue(commandValidator.isCommandValid("Create checking 12345678 0.01"));
	}

	@Test
	public void deposit_command() {
		assertTrue(commandValidator.isCommandValid("Deposit 87654321 50"));
	}
}
