package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferValidatorTest {

	CommandValidator commandValidator;
	Bank bank;
	Saving saving;
	Checking checking;
	CD cd;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		saving = new Saving(5, 12345678);
		checking = new Checking(5, 87654321);
		checking.deposit(500);
		cd = new CD(5, 2000, 11111111);
		saving.deposit(500);
		checking.deposit(500);
		bank.addAccount(saving);
		bank.addAccount(checking);
		bank.addAccount(cd);
		commandValidator = new CommandValidator(bank);
	}

	@Test
	public void transfer_between_checking_and_checking() {
		Checking checking2 = new Checking(5, 33333333);
		bank.addAccount(checking2);
		assertTrue(commandValidator.isCommandValid("transfer 87654321 33333333 250"));
	}

	@Test
	public void transfer_between_checking_and_saving() {
		assertTrue(commandValidator.isCommandValid("transfer 87654321 12345678 250"));
	}

	@Test
	public void transfer_between_saving_and_checking() {
		assertTrue(commandValidator.isCommandValid("transfer 12345678 87654321 250"));
	}

	@Test
	public void transfer_between_saving_and_saving() {
		Saving saving2 = new Saving(5, 44444444);
		bank.addAccount(saving2);
		assertTrue(commandValidator.isCommandValid("transfer 12345678 44444444 250"));
	}

	@Test
	public void cd_cannot_be_apart_of_fromId() {
		assertFalse(commandValidator.isCommandValid("transfer 11111111 12345678 250"));
	}

	@Test
	public void cd_cannot_be_apart_of_toId() {
		assertFalse(commandValidator.isCommandValid("transfer 12345678 11111111 250"));
	}

	@Test
	public void transfer_mispelled() {
		assertFalse(commandValidator.isCommandValid("tranfer 87654321 12345678 250"));
	}

	@Test
	public void fromId_does_not_exist() {
		assertFalse(commandValidator.isCommandValid("tranfer 87653321 12345678 250"));
	}

	@Test
	public void toId_does_not_exist() {
		assertFalse(commandValidator.isCommandValid("tranfer 87654321 12345478 250"));
	}

	@Test
	public void transfer_extra_arguments() {
		assertFalse(commandValidator.isCommandValid("transfer 87654321 12345678 250 john"));
	}

	@Test
	public void transfer_twice_from_same_account() {
		assertTrue(commandValidator.isCommandValid("transfer 87654321 12345678 250"));
		assertTrue(commandValidator.isCommandValid("transfer 87654321 12345678 250"));
	}

	@Test
	public void only_transfer_command() {
		assertFalse(commandValidator.isCommandValid("transfer"));
	}

	@Test
	public void only_transfer_and_fromid() {
		assertFalse(commandValidator.isCommandValid("transfer 87654321"));
	}

	@Test
	public void without_amount() {
		assertFalse(commandValidator.isCommandValid("transfer 87654321 12345678"));
	}

	@Test
	public void case_insenstive() {
		assertTrue(commandValidator.isCommandValid("tRaNsFer 87654321 12345678 250"));
	}

	@Test
	public void cannot_transfer_more_than_1000_into_checking() {
		saving.deposit(3000);
		assertFalse(commandValidator.isCommandValid("transfer 12345678 87654321 2500"));
	}

	@Test
	public void transfer_1000_into_checking() {
		saving.deposit(1000);
		assertTrue(commandValidator.isCommandValid("transfer 12345678 87654321 0"));
	}

	@Test
	public void cannot_transfer_more_than_2500_into_saving() {
		checking.deposit(3000);
		assertFalse(commandValidator.isCommandValid("transfer 87654321 12345678 3000"));
	}

	@Test
	public void fromID_it_not_8_digits() {
		assertFalse(commandValidator.isCommandValid("transfer 876543210 12345678 250"));
	}

	@Test
	public void toID_is_not_8_digits() {
		assertFalse(commandValidator.isCommandValid("transfer 87654321 123456780 250"));
	}

	@Test
	public void checking_withdraw_400() {
		checking.deposit(400);
		assertTrue(commandValidator.isCommandValid("transfer 87654321 12345678 400"));
	}

	@Test
	public void checking_cannot_withdraw_more_than_400() {
		assertFalse(commandValidator.isCommandValid("transfer 87654321 12345678 450"));
	}

	@Test
	public void all_string_command() {
		assertFalse(commandValidator.isCommandValid("transfer account1 acount2 fifty"));
	}

	@Test
	public void amount_is_in_words() {
		assertFalse(commandValidator.isCommandValid("transfer 87654321 12345678 fifty"));
	}

	@Test
	public void transfer_to_same_account() {
		assertFalse(commandValidator.isCommandValid("transfer 12345678 12345678 200"));
	}

}
