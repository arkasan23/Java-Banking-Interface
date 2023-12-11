package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositValidatorTest {

	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
		Saving saving = new Saving(5, 12345678);
		Checking checking = new Checking(5, 87654321);
		CD cd = new CD(5, 2000, 11111111);
		bank.addAccount(saving);
		bank.addAccount(checking);
		bank.addAccount(cd);
	}

	@Test
	public void deposit_into_checking_account() {
		assertTrue(commandValidator.isCommandValid("Deposit 12345678 500"));
	}

	@Test
	public void test() {
		assertFalse(commandValidator.isCommandValid("Deposit 500 500"));
	}

	@Test
	public void case_insensitivte() {
		assertTrue(commandValidator.isCommandValid("DEPOSIT 12345678 500"));
	}

	@Test
	public void misspelled_deposit() {
		assertFalse(commandValidator.isCommandValid("Depos 12345678 500"));
	}

	@Test
	public void account_id_is_less_than_8_digits() {
		assertFalse(commandValidator.isCommandValid("Deposit 50 500"));
	}

	@Test
	public void account_id_is_more_than_8_digits() {
		assertFalse(commandValidator.isCommandValid("Deposit 123456789 500"));
	}

	@Test
	public void depositing_more_than_2500_in_saving_account() {
		assertFalse(commandValidator.isCommandValid("Deposit 12345678 3000"));
	}

	@Test
	public void depositing_2500_in_saving_account() {
		assertTrue(commandValidator.isCommandValid("Deposit 12345678 2500"));
	}

	@Test
	public void depositing_1500_in_saving_account() {
		assertTrue(commandValidator.isCommandValid("Deposit 12345678 1500"));
	}

	@Test
	public void depositing_more_than_1000_in_checking_account() {
		assertFalse(commandValidator.isCommandValid("Deposit 87654321 1500"));
	}

	@Test
	public void depositing_1000_in_checking_account() {
		assertTrue(commandValidator.isCommandValid("Deposit 87654321 1000"));
	}

	@Test
	public void depositing_500_in_checking_account() {
		assertTrue(commandValidator.isCommandValid("Deposit 87654321 500"));
	}

	@Test
	public void depositing_negative_amount() {
		assertFalse(commandValidator.isCommandValid("Deposit 87654321 -500"));
	}

	@Test
	public void depositing_zero_amount() {
		assertTrue(commandValidator.isCommandValid("Deposit 12345678 0"));
	}

	@Test
	public void deposit_into_account_that_does_not_exist() {
		assertFalse(commandValidator.isCommandValid("Deposit 12345679 500"));
	}

	@Test
	public void deposit_into_string() {
		assertFalse(commandValidator.isCommandValid("deposit account 50"));
	}

	@Test
	public void amount_is_a_string() {
		assertFalse(commandValidator.isCommandValid("deposit 12345678 fifty"));
	}

	@Test
	public void deposit_into_CD() {
		assertFalse(commandValidator.isCommandValid("Deposit 11111111 500"));
	}

	@Test
	public void only_deposit_command() {
		assertFalse(commandValidator.isCommandValid("Deposit"));
	}

	@Test
	public void only_deposit_and_acountid_command() {
		assertFalse(commandValidator.isCommandValid("Deposit 12345679"));
	}

	@Test
	public void only_id_command() {
		assertFalse(commandValidator.isCommandValid("12345679"));
	}

	@Test
	public void deposit_twice() {
		assertTrue(commandValidator.isCommandValid("Deposit 12345678 500"));
		assertTrue(commandValidator.isCommandValid("Deposit 87654321 500"));
	}

	@Test
	public void depost_in_same_account_twice() {
		assertTrue(commandValidator.isCommandValid("Deposit 12345678 500"));
		assertTrue(commandValidator.isCommandValid("Deposit 12345678 500"));
	}

	@Test
	public void depost_in_same_account_three_times() {
		assertTrue(commandValidator.isCommandValid("Deposit 12345678 500"));
		assertTrue(commandValidator.isCommandValid("Deposit 12345678 1500"));
		assertTrue(commandValidator.isCommandValid("Deposit 12345678 750"));
	}

}
