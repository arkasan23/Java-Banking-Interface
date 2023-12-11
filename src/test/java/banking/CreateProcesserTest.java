package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateProcesserTest {

	CommandProcessor commandProcessor;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	public void create_checking_pass() {
		commandProcessor.processCommand("Create checking 12345678 0.01");
		Account account = bank.retrieveAccount(12345678);
		assertEquals(bank.getAccounts().size(), 1);
		assertTrue(account.getId() == 12345678 && account.getApr() == 0.01 && account.getAccountType() == "Checking"
				&& account.getBalance() == 0);
	}

	@Test
	public void create_saving() {
		commandProcessor.processCommand("Create savings 12345678 0.01");
		Account account = bank.retrieveAccount(12345678);
		assertEquals(bank.getAccounts().size(), 1);
		assertTrue(account.getId() == 12345678 && account.getApr() == 0.01 && account.getAccountType() == "Savings"
				&& account.getBalance() == 0);
	}

	@Test
	public void create_cd() {
		commandProcessor.processCommand("Create cd 12345678 0.01 2000");
		Account account = bank.retrieveAccount(12345678);
		assertEquals(bank.getAccounts().size(), 1);
		assertTrue(account.getId() == 12345678 && account.getApr() == 0.01 && account.getAccountType() == "Cd"
				&& account.getBalance() == 2000);
	}

	@Test
	public void case_insensitive() {
		commandProcessor.processCommand("CrEatE cHecKiNg 12345678 0.01");
		Account account = bank.retrieveAccount(12345678);
		assertEquals(bank.getAccounts().size(), 1);
		assertTrue(account.getId() == 12345678 && account.getApr() == 0.01 && account.getAccountType() == "Checking"
				&& account.getBalance() == 0);
	}

	@Test
	public void max_apr() {
		commandProcessor.processCommand("Create savings 12345678 10");
		Account account = bank.retrieveAccount(12345678);
		assertEquals(bank.getAccounts().size(), 1);
		assertTrue(account.getId() == 12345678 && account.getApr() == 10 && account.getAccountType() == "Savings"
				&& account.getBalance() == 0);
	}

	@Test
	public void zero_apr() {
		commandProcessor.processCommand("Create savings 12345678 0");
		Account account = bank.retrieveAccount(12345678);
		assertEquals(bank.getAccounts().size(), 1);
		assertTrue(account.getId() == 12345678 && account.getApr() == 0 && account.getAccountType() == "Savings"
				&& account.getBalance() == 0);
	}

	@Test
	public void cd_max_amount() {
		commandProcessor.processCommand("Create cd 12345678 5.4 10000");
		Account account = bank.retrieveAccount(12345678);
		assertEquals(bank.getAccounts().size(), 1);
		assertTrue(account.getId() == 12345678 && account.getApr() == 5.4 && account.getAccountType() == "Cd"
				&& account.getBalance() == 10000);
	}

	@Test
	public void cd_min_amount() {
		commandProcessor.processCommand("Create cd 12345678 5.4 1000");
		Account account = bank.retrieveAccount(12345678);
		assertEquals(bank.getAccounts().size(), 1);
		assertTrue(account.getId() == 12345678 && account.getApr() == 5.4 && account.getAccountType() == "Cd"
				&& account.getBalance() == 1000);
	}

	@Test
	public void create_two_accounts_in_a_row() {
		commandProcessor.processCommand("Create savings 12345678 0.01");
		commandProcessor.processCommand("Create checking 87654321 0.01");
		Account account1 = bank.retrieveAccount(12345678);
		Account account2 = bank.retrieveAccount(87654321);
		assertEquals(bank.getAccounts().size(), 2);
		assertTrue(account1.getId() == 12345678 && account1.getApr() == 0.01 && account1.getAccountType() == "Savings"
				&& account1.getBalance() == 0);
		assertTrue(account2.getId() == 87654321 && account2.getApr() == 0.01 && account2.getAccountType() == "Checking"
				&& account2.getBalance() == 0);
	}

	@Test
	public void create_three_accounts_in_a_row() {
		commandProcessor.processCommand("Create savings 12345678 0.01");
		commandProcessor.processCommand("Create checking 87654321 0.01");
		commandProcessor.processCommand("Create cd 12345679 0.01 2000");
		Account account1 = bank.retrieveAccount(12345678);
		Account account2 = bank.retrieveAccount(87654321);
		Account account3 = bank.retrieveAccount(12345679);
		assertEquals(bank.getAccounts().size(), 3);
		assertTrue(account1.getId() == 12345678 && account1.getApr() == 0.01 && account1.getAccountType() == "Savings"
				&& account1.getBalance() == 0);
		assertTrue(account2.getId() == 87654321 && account2.getApr() == 0.01 && account2.getAccountType() == "Checking"
				&& account2.getBalance() == 0);
		assertTrue(account3.getId() == 12345679 && account3.getApr() == 0.01 && account3.getAccountType() == "Cd"
				&& account3.getBalance() == 2000);
	}

	@Test
	public void create_a_account_with_account_in_bank_already() {
		Saving saving = new Saving(5.0, 12345678);
		bank.addAccount(saving);
		commandProcessor.processCommand("Create checking 87654321 0.01");
		Account account = bank.retrieveAccount(87654321);
		assertEquals(bank.getAccounts().size(), 2);
		assertTrue(account.getId() == 87654321 && account.getApr() == 0.01 && account.getAccountType() == "Checking"
				&& account.getBalance() == 0);
	}

	@Test
	public void create_a_account_with_two_account_in_bank_already() {
		Saving saving = new Saving(5.0, 12345678);
		Checking checking = new Checking(3.4, 12345679);
		bank.addAccount(checking);
		bank.addAccount(saving);
		commandProcessor.processCommand("Create checking 87654321 0.01");
		Account account = bank.retrieveAccount(87654321);
		assertEquals(bank.getAccounts().size(), 3);
		assertTrue(account.getId() == 87654321 && account.getApr() == 0.01 && account.getAccountType() == "Checking"
				&& account.getBalance() == 0);
	}

	@Test
	public void create_a_account_with_three_account_in_bank_already() {
		Saving saving = new Saving(5.0, 12345678);
		Checking checking = new Checking(3.4, 12345679);
		CD cd = new CD(7.4, 2000, 12345658);
		bank.addAccount(cd);
		bank.addAccount(checking);
		bank.addAccount(saving);
		commandProcessor.processCommand("Create checking 87654321 0.01");
		Account account = bank.retrieveAccount(87654321);
		assertEquals(bank.getAccounts().size(), 4);
		assertTrue(account.getId() == 87654321 && account.getApr() == 0.01 && account.getAccountType() == "Checking"
				&& account.getBalance() == 0);
	}

}
