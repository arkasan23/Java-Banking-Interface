package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeProcessorTest {

	CommandProcessor commandProcessor;
	CommandValidator commandValidator;
	Bank bank;
	Checking checking;
	Saving saving;
	CD cd;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
		commandProcessor = new CommandProcessor(bank);
		checking = new Checking(5.0, 12345678);
		saving = new Saving(5, 87654321);
		saving.deposit(75);
		checking.deposit(25);
		bank.addAccount(checking);
		bank.addAccount(saving);

	}

	@Test
	public void pass_time_account_balance_above_100() {
		cd = new CD(5, 1000, 24547658);
		bank.addAccount(cd);
		commandProcessor.processCommand("pass 1");
		double actual = cd.getBalance();
		assertEquals(1016.7711229865934, actual);
	}

	@Test
	public void pass_command_twice_in_a_row() {
		commandProcessor.processCommand("pass 1");
		commandProcessor.processCommand("pass 1");
		int actual = bank.getAccounts().size();
		assertEquals(1, actual);
		assertEquals(25.313368055555557, saving.getBalance());
	}

	@Test
	public void account_balance_below_100() {
		commandProcessor.processCommand("pass 1");
		double actual = saving.getBalance();
		assertEquals(50.208333333333336, actual);
	}

	@Test
	public void account_balance_below_100_twice() {
		commandProcessor.processCommand("pass 2");
		double actual = saving.getBalance();
		assertEquals(25.313368055555557, actual);
	}

	@Test
	public void account_balance_at_100() {
		checking.setBalance(100);
		commandProcessor.processCommand("pass 1");
		double actual = checking.getBalance();
		assertEquals(100.41666666666667, actual);
	}

	@Test
	public void close_account_if_balance_is_zero() {
		commandProcessor.processCommand("pass 2");
		int actual = bank.getAccounts().size();
		assertEquals(1, actual);
		assertFalse(bank.getAccounts().containsKey(12345678));
	}

	@Test
	public void close_multiple_accounts() {
		saving.withdraw(50);
		commandProcessor.processCommand("pass 2");
		int actual = bank.getAccounts().size();
		assertEquals(0, actual);
		assertFalse(bank.getAccounts().containsKey(12345678));
		assertFalse(bank.getAccounts().containsKey(87654321));
	}

	@Test
	public void pass_60_months() {
		commandProcessor.processCommand("pass 60");
		int actual = bank.getAccounts().size();
		assertEquals(0, actual);
		assertFalse(bank.getAccounts().containsKey(12345678));
		assertFalse(bank.getAccounts().containsKey(87654321));
	}

	@Test
	public void cd_account_apr_calulcation() {
		cd = new CD(2.1, 2000, 44444444);
		bank.addAccount(cd);
		commandProcessor.processCommand("pass 1");
		double actual = cd.getBalance();
		assertEquals(actual, 2014.0367928937578);
	}

	@Test
	public void apr_calculation_of_saving_balance_of_1000() {
		saving = new Saving(3, 22222222);
		saving.deposit(1000);
		bank.addAccount(saving);
		commandProcessor.processCommand("pass 1");
		double actual = saving.getBalance();
		assertEquals(actual, 1002.5);
	}

	@Test
	public void apr_calculation_of_checking() {
		checking = new Checking(3, 55555555);
		checking.deposit(1000);
		bank.addAccount(checking);
		commandProcessor.processCommand("pass 1");
		double actual = checking.getBalance();
		assertEquals(actual, 1002.5);
	}

	@Test
	public void check_if_saving_withdraw_is_valid_after_pass_time() {
		Saving saving = new Saving(5, 12345678);
		saving.deposit(1000);
		bank.addAccount(saving);
		assertTrue(commandValidator.isCommandValid("withdraw 12345678 50"));
		commandProcessor.processCommand("withdraw 12345678 50");
		assertFalse(commandValidator.isCommandValid("withdraw 12345678 100"));
		commandProcessor.processCommand("pass 1");
		assertTrue(commandValidator.isCommandValid("withdraw 12345678 100"));
	}

	@Test
	public void check_if_withdraw_valid_for_cd() {
		CD cd = new CD(5, 2000, 55555555);
		bank.addAccount(cd);
		assertFalse(commandValidator.isCommandValid("withdraw 55555555 3000"));
		commandProcessor.processCommand("pass 12");
		assertTrue(commandValidator.isCommandValid("withdraw 55555555 3000"));
	}

}
