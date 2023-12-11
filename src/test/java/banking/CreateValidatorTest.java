package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateValidatorTest {

	CommandValidator commandValidator;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	public void checking_creation_pass() {
		assertTrue(commandValidator.isCommandValid("Create checking 12345678 0.01"));
	}

	@Test
	public void checking_typo_creation_pass() {

		assertFalse(commandValidator.isCommandValid("Create chekcing 12345678 0.01"));
	}

	@Test
	public void case_insensitive() {

		assertTrue(commandValidator.isCommandValid("CREATE CHECKING 12345678 0.01"));
	}

	@Test
	public void savings_creation_pass() {

		assertTrue(commandValidator.isCommandValid("Create savings 12345678 0.01"));
	}

	@Test
	public void cd_creation() {

		assertTrue(commandValidator.isCommandValid("Create cd 12345678 1.2 2000"));
	}

	@Test
	public void apr_at_max_value() {

		assertTrue(commandValidator.isCommandValid("Create savings 12345678 10"));
	}

	@Test
	public void apr_at_min_value() {
		assertTrue(commandValidator.isCommandValid("Create savings 12345678 0"));
	}

	@Test
	public void apr_above_max_value() {

		assertFalse(commandValidator.isCommandValid("Create savings 12345678 13"));
	}

	@Test
	public void apr_is_negative() {

		assertFalse(commandValidator.isCommandValid("Create savings 12345678 -5"));
	}

	@Test
	public void apr_is_a_decimal() {

		assertTrue(commandValidator.isCommandValid("Create savings 12345678 3.4"));
	}

	@Test
	public void cd_without_a_balance() {

		assertFalse(commandValidator.isCommandValid("Create cd 12345678 5"));
	}

	@Test
	public void create_only() {

		assertFalse(commandValidator.isCommandValid("Create"));
	}

	@Test
	public void savings_only() {

		assertFalse(commandValidator.isCommandValid("Create saving"));
	}

	@Test
	public void checking_and_account_id_only() {

		assertFalse(commandValidator.isCommandValid("Create saving 12345678"));
	}

	@Test
	public void only_account_id_and_apr_and_balance() {

		assertFalse(commandValidator.isCommandValid("12345678 3.4 1000"));
	}

	@Test
	public void invalid_account_id() {

		assertFalse(commandValidator.isCommandValid("Create cd 40 3.4 1000"));
	}

	@Test
	public void below_balance_min_cd() {

		assertFalse(commandValidator.isCommandValid("Create cd 12345678 5 999"));
	}

	@Test
	public void above_balance_max_cd() {

		assertFalse(commandValidator.isCommandValid("Create cd 12345678 5 10001"));
	}

	@Test
	public void balance_is_at_zero() {

		assertFalse(commandValidator.isCommandValid("Create cd 12345678 5 0"));
	}

	@Test
	public void balance_is_negative() {

		assertFalse(commandValidator.isCommandValid("Create cd 12345678 5 -500"));
	}

	@Test
	public void balance_is_at_min_value() {

		assertTrue(commandValidator.isCommandValid("Create cd 12345678 5 1000"));
	}

	@Test
	public void balance_is_at_max_value() {

		assertTrue(commandValidator.isCommandValid("Create cd 12345678 5 10000"));
	}

	@Test
	public void duplicate_account_with_same_id_already_exists() {
		Checking testAccount = new Checking(.01, 12345678);
		bank.addAccount(testAccount);

		assertFalse(commandValidator.isCommandValid("Create checking 12345678 0.01"));
	}

	@Test
	public void test() {
		assertTrue(commandValidator.isCommandValid("Create savings 12345678 0.6"));
	}

	@Test
	public void bank_with_1_account_exist() {
		Checking testAccount = new Checking(.01, 12345678);
		bank.addAccount(testAccount);

		assertTrue(commandValidator.isCommandValid("Create checking 48765432 0.01"));
	}

	@Test
	public void bank_with_2_account_exist() {
		Checking testAccount = new Checking(.01, 12345678);
		bank.addAccount(testAccount);
		Saving testAccount2 = new Saving(.05, 12355678);
		bank.addAccount(testAccount2);

		assertTrue(commandValidator.isCommandValid("Create checking 48765432 0.01"));
	}

	@Test
	public void bank_with_3_accounts_already_exist() {
		Checking testAccount = new Checking(.01, 12345678);
		bank.addAccount(testAccount);
		Saving testAccount2 = new Saving(.05, 12355678);
		bank.addAccount(testAccount2);
		CD testAccount3 = new CD(.05, 48765432, 1500);
		bank.addAccount(testAccount3);

		assertTrue(commandValidator.isCommandValid("Create cd 98765432 5 2000"));
	}

	@Test
	public void wrong_account_type() {

		assertFalse(commandValidator.isCommandValid("Create deposit 12345678 .5"));
	}

	@Test
	public void saving_with_balance() {

		assertFalse(commandValidator.isCommandValid("Create saving 12345678 5 1500"));
	}

	@Test
	public void saving_with_multiple_fields_after() {

		assertFalse(commandValidator.isCommandValid("Create saving 12345678 5 1500 John 21"));
	}

	@Test
	public void checking_with_multiple_fields_after() {

		assertFalse(commandValidator.isCommandValid("Create saving 12345678 5 25 500 John Doe"));
	}

	@Test
	public void cd_with_multiple_fields_after() {

		assertFalse(commandValidator.isCommandValid("Create cd 12345678 5 1500 500 John Doe"));
	}

	@Test
	public void more_than_8_digits_for_account() {

		assertFalse(commandValidator.isCommandValid("Create checking 123456789 .5"));
	}

	@Test
	public void less_than_8_digits_for_account() {

		assertFalse(commandValidator.isCommandValid("Create checking 1234567 .5"));
	}

	@Test
	public void all_strings() {
		assertFalse(commandValidator.isCommandValid("create checking onetwothree pointfive"));
	}
}
