package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {

	MasterControl masterControl;
	List input;

	public void assertSingleCommand(String command, List<String> acutal) {
		assertEquals(1, acutal.size());
		assertEquals(command, acutal.get(0));
	}

	@BeforeEach
	public void setUp() {
		input = new ArrayList<>();
		Bank bank = new Bank();
		masterControl = new MasterControl(new CommandValidator(bank), new CommandProcessor(bank), new CommandStorage());
	}

	@Test
	public void typo_in_create_command_is_invalid() {
		input.add("creat checking 12345678 1.0");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("creat checking 12345678 1.0", actual);
	}

	@Test
	public void typo_in_deposit_command_is_invalid() {
		input.add("depossit 12345678 100");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("depossit 12345678 100", actual);
	}

	@Test
	public void two_typo_commands_both_invalid() {
		input.add("creat checking 12345678 1.0");
		input.add("depossit 12345678 100");
		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("creat checking 12345678 1.0", actual.get(0));
		assertEquals("depossit 12345678 100", actual.get(1));
	}

	@Test
	public void invalid_to_create_accounts_with_same_accounts() {
		input.add("create checking 12345678 1.0");
		input.add("create checking 12345678 1.0");

		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("Checking 12345678 0.00 1.00", actual.get(0));
		assertEquals("create checking 12345678 1.0", actual.get(1));
	}

	@Test
	public void create_account() {
		input.add("create checking 12345678 1.0");
		input.add("deposit 12345678 500");

		List<String> actual = masterControl.start(input);
		assertEquals("Checking 12345678 500.00 1.00", actual.get(0));
	}

	@Test
	void sample_make_sure_this_passes_unchanged() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678 5000");
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
		assertEquals("Deposit 12345678 5000", actual.get(4));
	}

	@Test
	public void another_sample_test_case_with_withdrawl() {
		input.add("Create savings 12345678 5");
		input.add("Create checking 87654321 3");
		input.add("Deposit 12345678 1500");
		input.add("Withdraw 12345678 250");
		input.add("Withdraw 87654321 250");
		input.add("Deposit 87654321 500");
		List<String> actual = masterControl.start(input);

		assertEquals(6, actual.size());
		assertEquals("Savings 12345678 1250.00 5.00", actual.get(0));
		assertEquals("Deposit 12345678 1500", actual.get(1));
		assertEquals("Withdraw 12345678 250", actual.get(2));
		assertEquals("Checking 87654321 500.00 3.00", actual.get(3));
		assertEquals("Withdraw 87654321 250", actual.get(4));
		assertEquals("Deposit 87654321 500", actual.get(5));
	}

	@Test
	public void transfer_shows_up_in_both_accounts() {
		input.add("Create checking 12345678 2.3");
		input.add("Create checking 23456789 3.2");
		input.add("Deposit 23456789 400");
		input.add("Transfer 23456789 12345678 400");
		List<String> actual = masterControl.start(input);

		System.out.println(actual);
		assertEquals(5, actual.size());
		assertEquals("Checking 12345678 400.00 2.30", actual.get(0));
		assertEquals("Transfer 23456789 12345678 400", actual.get(1));
		assertEquals("Checking 23456789 0.00 3.20", actual.get(2));
		assertEquals("Deposit 23456789 400", actual.get(3));
		assertEquals("Transfer 23456789 12345678 400", actual.get(4));
	}
}
