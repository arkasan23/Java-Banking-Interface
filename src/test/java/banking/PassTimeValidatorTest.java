package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeValidatorTest {

	CommandValidator commandValidator;
	Bank bank;
	CommandProcessor commandProcessor;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
		commandValidator = new CommandValidator(bank);
	}

	@Test
	public void pass_1_month() {
		assertTrue(commandValidator.isCommandValid("pass 1"));
	}

	@Test
	public void pass_one_and_half_months() {
		assertFalse(commandValidator.isCommandValid("pass 1.5"));
	}

	@Test
	public void pass_5_month() {
		assertTrue(commandValidator.isCommandValid("pass 5"));
	}

	@Test
	public void pass_60_months() {
		assertTrue(commandValidator.isCommandValid("pass 60"));
	}

	@Test
	public void pass_negative_months() {
		assertFalse(commandValidator.isCommandValid("pass -1"));
	}

	@Test
	public void case_insensitivte() {
		assertTrue(commandValidator.isCommandValid("PASS 1"));
	}

	@Test
	public void pass_and_string_after() {
		assertFalse(commandValidator.isCommandValid("pass two"));
	}

	@Test
	public void misspelled_pass() {
		assertFalse(commandValidator.isCommandValid("pas 1"));
	}

	@Test
	public void only_pass() {
		assertFalse(commandValidator.isCommandValid("pass"));
	}

	@Test
	public void only_string() {
		assertFalse(commandValidator.isCommandValid("pass five"));
	}

	@Test
	public void multiple_arguments_after() {
		assertFalse(commandValidator.isCommandValid("pass 5 checking"));
	}

	@Test
	public void only_amount() {
		assertFalse(commandValidator.isCommandValid("5"));
	}

	@Test
	public void pass_twice() {
		assertTrue(commandValidator.isCommandValid("pass 1"));
		assertTrue(commandValidator.isCommandValid("pass 1"));
	}

	@Test
	public void pass_thrice() {
		assertTrue(commandValidator.isCommandValid("pass 1"));
		assertTrue(commandValidator.isCommandValid("pass 2"));
		assertTrue(commandValidator.isCommandValid("pass 5"));
	}

	@Test
	public void pass_0_month() {
		assertFalse(commandValidator.isCommandValid("pass 0"));
	}

	@Test
	public void pass_61_month() {
		assertFalse(commandValidator.isCommandValid("pass 61"));
	}

}
