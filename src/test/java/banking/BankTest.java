package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {

	final int DEFAULT_APR = 8;
	final int DEFAULT_ID = 89456185;
	final double TEST_AMOUNT = 10;

	Bank bank;
	Checking testAccount;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		testAccount = new Checking(DEFAULT_APR, DEFAULT_ID);
		bank.addAccount(testAccount);
		testAccount.setBalance(TEST_AMOUNT * 2);
	}

	@Test
	public void bank_initially_has_no_accounts() {
		bank = new Bank();
		assertTrue(bank.getAccounts().isEmpty());
	}

	@Test
	public void add_one_account_to_bank() {
		assertEquals(1, bank.getAccounts().size());
	}

	@Test
	public void add_two_account_to_bank() {
		Saving testAccountTwo = new Saving(DEFAULT_APR, DEFAULT_ID + 1);
		bank.addAccount(testAccountTwo);

		assertEquals(2, bank.getAccounts().size());
	}

	@Test
	public void retrieve_correct_account() {
		Account actual = bank.retrieveAccount(DEFAULT_ID);
		assertEquals(testAccount, actual);
	}

	@Test
	public void deposit_money_by_id() {
		bank.depositByID(DEFAULT_ID, TEST_AMOUNT);
		double actual = testAccount.getBalance();

		assertEquals(actual, 30);
	}

	@Test
	public void withdraw_money_by_id() {
		bank.withdrawByID(DEFAULT_ID, TEST_AMOUNT);
		double actual = testAccount.getBalance();

		assertEquals(actual, 10);
	}

	@Test
	public void deposit_twice() {
		bank.depositByID(DEFAULT_ID, TEST_AMOUNT);
		bank.depositByID(DEFAULT_ID, TEST_AMOUNT);
		double actual = testAccount.getBalance();

		assertEquals(actual, 40);
	}

	@Test
	public void withdraw_twice() {
		bank.withdrawByID(DEFAULT_ID, TEST_AMOUNT);
		bank.withdrawByID(DEFAULT_ID, TEST_AMOUNT);
		double actual = testAccount.getBalance();

		assertEquals(actual, 0);
	}

}
