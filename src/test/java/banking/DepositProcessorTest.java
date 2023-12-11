package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositProcessorTest {

	CommandProcessor commandProcessor;
	Bank bank;
	Checking checking;
	Saving saving;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
		checking = new Checking(5.0, 12345678);
		saving = new Saving(5, 87654321);
		bank.addAccount(checking);
		bank.addAccount(saving);
	}

	@Test
	public void deposit_into_checking() {
		commandProcessor.processCommand("deposit 12345678 100");
		double actual = checking.getBalance();
		assertEquals(actual, 100);
	}

	@Test
	public void deposit_into_saving() {
		commandProcessor.processCommand("deposit 87654321 100");
		double actual = saving.getBalance();
		assertEquals(actual, 100);
	}

	@Test
	public void deposit_into_account_with_money_already() {
		saving.deposit(100);
		commandProcessor.processCommand("deposit 87654321 100");
		double actual = saving.getBalance();
		assertEquals(actual, 200);
	}

	@Test
	public void deposit_twice_in_same_account() {
		commandProcessor.processCommand("deposit 87654321 100");
		commandProcessor.processCommand("deposit 87654321 100");
		double actual = saving.getBalance();
		assertEquals(actual, 200);
	}

	@Test
	public void deposit_three_times_in_same_account() {
		commandProcessor.processCommand("deposit 87654321 100");
		commandProcessor.processCommand("deposit 87654321 100");
		commandProcessor.processCommand("deposit 87654321 100");
		double actual = saving.getBalance();
		assertEquals(actual, 300);
	}

	@Test
	public void deposit_into_seperate_accounts() {
		commandProcessor.processCommand("deposit 87654321 100");
		commandProcessor.processCommand("deposit 12345678 100");
		double actual1 = saving.getBalance();
		double actual2 = checking.getBalance();
		assertEquals(actual1, 100);
		assertEquals(actual2, 100);
	}

	@Test
	public void deposit_zero() {
		commandProcessor.processCommand("deposit 87654321 0");
		double actual = saving.getBalance();
		assertEquals(actual, 0);
	}

	@Test
	public void deposit_max_deposit_amount_for_checking() {
		commandProcessor.processCommand("deposit 12345678 1000");
		double actual = checking.getBalance();
		assertEquals(actual, 1000);
	}

	@Test
	public void deposit_max_deposit_amount_for_saving() {
		commandProcessor.processCommand("deposit 87654321 2500");
		double actual = saving.getBalance();
		assertEquals(actual, 2500);
	}

}
