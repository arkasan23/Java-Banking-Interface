package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferProcessorTest {

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
	public void transfer_from_checking_to_saving() {
		commandProcessor.processCommand("transfer 12345678 87654321 1000");
		double actual = saving.getBalance();
		assertEquals(2000, actual);
		assertEquals(0, checking.getBalance());
	}

	@Test
	public void transfer_from_saving_to_checking() {
		commandProcessor.processCommand("transfer 87654321 12345678 400");
		double actual = checking.getBalance();
		assertEquals(1400, actual);
		assertEquals(600, saving.getBalance());
	}

	@Test
	public void transfer_from_checking_to_checking() {
		Checking checking2 = new Checking(3, 22222222);
		checking2.deposit(250);
		bank.addAccount(checking2);
		commandProcessor.processCommand("transfer 22222222 12345678 250");
		double actual = checking.getBalance();
		double actual2 = checking2.getBalance();
		assertEquals(1250, actual);
		assertEquals(0, actual2);
	}

	@Test
	public void transfer_from_saving_to_saving() {
		Saving saving2 = new Saving(3, 11111111);
		saving2.deposit(400);
		bank.addAccount(saving2);
		commandProcessor.processCommand("transfer 11111111 87654321 400");
		double actual = saving.getBalance();
		double actual2 = saving2.getBalance();
		assertEquals(1400, actual);
		assertEquals(0, actual2);
	}

	@Test
	public void only_deposit_amount_actually_withdrawn() {
		saving.setBalance(250);
		commandProcessor.processCommand("transfer 87654321 12345678 300");
		double actual = checking.getBalance();
		double actual2 = saving.getBalance();
		assertEquals(1250, actual);
		assertEquals(0, actual2);
	}

	@Test
	public void transfer_twice_from_checking() {
		commandProcessor.processCommand("transfer 12345678 87654321 300");
		commandProcessor.processCommand("transfer 12345678 87654321 300");
		double actual1 = saving.getBalance();
		double actual2 = checking.getBalance();
		assertEquals(1600, actual1);
		assertEquals(400, actual2);
	}

	@Test
	public void transfer_each_way_once() {
		commandProcessor.processCommand("transfer 12345678 87654321 300");
		commandProcessor.processCommand("transfer 87654321 12345678 300");
		double actual1 = saving.getBalance();
		double actual2 = checking.getBalance();
		assertEquals(1000, actual1);
		assertEquals(1000, actual2);
	}

	@Test
	public void transfer_max_from_checking() {
		commandProcessor.processCommand("transfer 12345678 87654321 400");
		double actual1 = saving.getBalance();
		double actual2 = checking.getBalance();
		assertEquals(1400, actual1);
		assertEquals(600, actual2);
	}

	@Test
	public void transfer_max_from_saving() {
		commandProcessor.processCommand("transfer 87654321 12345678 1000");
		double actual1 = saving.getBalance();
		double actual2 = checking.getBalance();
		assertEquals(0, actual1);
		assertEquals(2000, actual2);
	}

	@Test
	public void transfer_zero() {
		commandProcessor.processCommand("transfer 12345678 87654321 0");
		double actual1 = saving.getBalance();
		double actual2 = checking.getBalance();
		assertEquals(1000, actual1);
		assertEquals(1000, actual2);
	}

}
