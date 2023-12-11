package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawValidatorTest {

	CommandValidator commandValidator;
	CommandProcessor commandProcessor;
	Bank bank;
	Saving saving;
	Checking checking;
	CD cd;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
		saving = new Saving(5, 12345678);
		checking = new Checking(5, 87654321);
		cd = new CD(5, 2000, 11111111);

		checking.deposit(1000);
		saving.deposit(1000);

		bank.addAccount(saving);
		bank.addAccount(checking);
		bank.addAccount(cd);

		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	public void withdraw_from_checking_account() {
		assertTrue(commandValidator.isCommandValid("Withdraw 87654321 200"));
	}

	@Test
	public void withdraw_from_saving_account() {
		assertTrue(commandValidator.isCommandValid("Withdraw 12345678 200"));
	}

	@Test
	public void withdraw_max_checking_account() {
		assertTrue(commandValidator.isCommandValid("Withdraw 87654321 400"));
	}

	@Test
	public void withdraw_more_than_max_for_checking() {
		assertFalse(commandValidator.isCommandValid("Withdraw 87654321 401"));
	}

	@Test
	public void withdraw_max_saving_account() {
		assertTrue(commandValidator.isCommandValid("Withdraw 12345678 1000"));
	}

	@Test
	public void withdraw_more_than_max_for_saving() {
		assertFalse(commandValidator.isCommandValid("Withdraw 12345678 1001"));
	}

	@Test
	public void can_withdraw_zero() {
		assertTrue(commandValidator.isCommandValid("Withdraw 12345678 0"));
	}

	@Test
	public void cant_withdraw_below_zero() {
		assertFalse(commandValidator.isCommandValid("Withdraw 12345678 -1"));
	}

	@Test
	public void withdraw_mutliple_twice_from_checking() {
		assertTrue(commandValidator.isCommandValid("Withdraw 87654321 200"));
		checking.withdraw(200);
		assertTrue(commandValidator.isCommandValid("Withdraw 87654321 200"));
	}

	@Test
	public void cannot_withdraw_twice_in_a_month_from_saving() {
		assertTrue(commandValidator.isCommandValid("Withdraw 12345678 1000"));
		saving.withdraw(1000);
		assertFalse(commandValidator.isCommandValid("Withdraw 12345678 1000"));
	}

	@Test
	public void can_withdraw_from_saving_after_a_month() {
		assertTrue(commandValidator.isCommandValid("Withdraw 12345678 50"));
		saving.withdraw(50);
		commandProcessor.processCommand("pass 1");
		assertTrue(commandValidator.isCommandValid("Withdraw 12345678 50"));
	}

	@Test
	public void withdraw_and_string_after() {
		assertFalse(commandValidator.isCommandValid("withdraw account five"));
	}

	@Test
	public void amount_in_words() {
		assertFalse(commandValidator.isCommandValid("withdraw 12345678 fifty"));
	}

	@Test
	public void withdraw_full_amount_from_cd_after_12_month() {
		commandProcessor.processCommand("pass 12");
		assertTrue(commandValidator.isCommandValid("withdraw 11111111 2500"));
	}

	@Test
	public void withdraw_full_amount_from_cd_after_12_month_more_than_account_has() {
		commandProcessor.processCommand("pass 12");
		assertTrue(commandValidator.isCommandValid("withdraw 11111111 3000"));
	}

	@Test
	public void cannot_withdraw_from_cd_before_12_month() {
		assertFalse(commandValidator.isCommandValid("withdraw 11111111 2000"));
	}

	@Test
	public void only_with_withdraw_command() {
		assertFalse(commandValidator.isCommandValid("withdraw"));
	}

	@Test
	public void without_amount_specified() {
		assertFalse(commandValidator.isCommandValid("withdraw 12345678"));
	}

	@Test
	public void extra_arguments_after_command() {
		assertFalse(commandValidator.isCommandValid("withdraw 12345678 50 jack!"));
	}

	@Test
	public void with_only_amount() {
		assertFalse(commandValidator.isCommandValid("50"));
	}

	@Test
	public void misspelled_withdraw() {
		assertFalse(commandValidator.isCommandValid("wihdraw 12345678 50"));
	}

	@Test
	public void account_id_is_less_than_8_digits() {
		assertFalse(commandValidator.isCommandValid("withdraw 1234567 50"));
	}

	@Test
	public void account_id_is_more_than_8_digits() {
		assertFalse(commandValidator.isCommandValid("withdraw 123456789 50"));
	}

	@Test
	public void withdraw_from_account_that_doesnt_exist() {
		assertFalse(commandValidator.isCommandValid("withdraw 12346678 50"));
	}

	@Test
	public void case_insenstive() {
		assertTrue(commandValidator.isCommandValid("WiThDrAw 12345678 50"));
	}

	@Test
	public void withdraw_exact_cd_amount() {
		commandProcessor.processCommand("pass 12");
		assertTrue(commandValidator.isCommandValid("withdraw 11111111 2441.7907100508396"));
	}

	@Test
	public void withdraw_less_than_cd_amount() {
		commandProcessor.processCommand("pass 12");
		assertFalse(commandValidator.isCommandValid("withdraw 11111111 2000"));
	}
}
