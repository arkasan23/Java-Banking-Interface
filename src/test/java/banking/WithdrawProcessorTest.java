package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawProcessorTest {

	CommandProcessor commandProcessor;
	Bank bank;
	Checking checking;
	Saving saving;
	CD cd;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
		checking = new Checking(5.0, 12345678);
		saving = new Saving(5, 87654321);
		cd = new CD(5, 2000, 11111111);
		checking.deposit(1000);
		saving.deposit(1000);
		bank.addAccount(checking);
		bank.addAccount(saving);
		bank.addAccount(cd);
	}

	@Test
	public void withdraw_from_checking() {
		commandProcessor.processCommand("withdraw 12345678 200");
		double actual = checking.getBalance();
		assertEquals(800, actual);
	}

	@Test
	public void withdraw_from_saving() {
		commandProcessor.processCommand("withdraw 87654321 200");
		double actual = saving.getBalance();
		assertEquals(800, actual);
	}

	@Test
	public void withdraw_max_from_checking() {
		commandProcessor.processCommand("withdraw 12345678 400");
		double actual = checking.getBalance();
		assertEquals(600, actual);
	}

	@Test
	public void withdraw_max_from_saving() {
		commandProcessor.processCommand("withdraw 87654321 1000");
		double actual = saving.getBalance();
		assertEquals(0, actual);
	}

	@Test
	public void withdraw_zero() {
		commandProcessor.processCommand("withdraw 12345678 0");
		double actual = checking.getBalance();
		assertEquals(1000, actual);
	}

	@Test
	public void withdraw_multiple_times_from_checking() {
		commandProcessor.processCommand("withdraw 12345678 100");
		commandProcessor.processCommand("withdraw 12345678 100");
		double actual = checking.getBalance();
		assertEquals(800, actual);
	}

	@Test
	public void withdraw_after_month_has_pass() {
		commandProcessor.processCommand("withdraw 87654321 100");
		commandProcessor.processCommand("pass 1");
		commandProcessor.processCommand("withdraw 87654321 100");
		double actual = saving.getBalance();
		assertEquals(803.75, actual);
	}

	@Test
	public void withdraw_cd_after_12_months() {
		commandProcessor.processCommand("pass 12");
		commandProcessor.processCommand("withdraw 11111111 2450");
		double actual = cd.getBalance();
		assertEquals(0, actual);
	}

	@Test
	public void withdraw_more_than_in_checking_balance() {
		checking.setBalance(300);
		commandProcessor.processCommand("withdraw 12345678 400");
		double actual = checking.getBalance();
		assertEquals(0, actual);
	}

	@Test
	public void withdraw_more_than_cd_balance() {
		commandProcessor.processCommand("pass 12");
		commandProcessor.processCommand("withdraw 11111111 2500");
		double actual = cd.getBalance();
		assertEquals(0, actual);
	}

	@Test
	public void withdraw_from_multiple_accounts() {
		commandProcessor.processCommand("withdraw 12345678 100");
		commandProcessor.processCommand("withdraw 87654321 100");
		double actual1 = saving.getBalance();
		double actual2 = checking.getBalance();
		assertEquals(900, actual1);
		assertEquals(900, actual2);
	}

	@Test
	public void withdraw_from_account_that_has_no_money() {
		checking.setBalance(0);
		commandProcessor.processCommand("withdraw 12345678 100");
		double actual = checking.getBalance();
		assertEquals(0, actual);
	}
}
