package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {

	final int DEFAULT_APR = 8;
	final int DEFAULT_ID = 89456185;
	final double TEST_AMOUNT = 10;
	Account account;

	@BeforeEach
	public void setUp() {
		account = new Checking(DEFAULT_APR, DEFAULT_ID);
		account.setBalance(TEST_AMOUNT * 2);
	}

	@Test
	public void apr_is_supplied_apr_when_account_created() {
		double actual = account.getApr();

		assertEquals(DEFAULT_APR, actual);
	}

	@Test
	public void test() {
		account.setBalance(-10);
		account.withdraw(10);
		double actual = account.getBalance();

		assertEquals(0, actual);
	}

	@Test
	public void balance_increases_by_the_amount_deposited() {
		account.deposit(TEST_AMOUNT);
		double actual = account.getBalance();

		assertEquals(TEST_AMOUNT * 3, actual);
	}

	@Test
	public void balance_decreases_by_amount_withdrawn() {
		account.withdraw(TEST_AMOUNT);
		double actual = account.getBalance();

		assertEquals(TEST_AMOUNT, actual);
	}

	@Test
	public void balance_cannot_be_negative_when_withdrawing() {
		account.withdraw(TEST_AMOUNT * 3);
		double actual = account.getBalance();

		assertEquals(0, actual);
	}

	@Test
	public void depositing_twice() {
		account.deposit(TEST_AMOUNT);
		account.deposit(TEST_AMOUNT);
		double actual = account.getBalance();

		assertEquals(TEST_AMOUNT * 4, actual);
	}

	@Test
	public void withdrawing_twice() {
		account.withdraw(TEST_AMOUNT);
		account.withdraw(TEST_AMOUNT);
		double actual = account.getBalance();

		assertEquals(0, actual);
	}

}