package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStorageTest {

	CommandStorage commandStorage;

	@BeforeEach
	void setUp() {
		commandStorage = new CommandStorage();
	}

	@Test
	public void add_invalid_command_to_invalid_storage() {
		commandStorage.addInvalidCommand("Crate deposit 403033");
		List<String> actual = commandStorage.getInvalidCommands();
		assertEquals(1, actual.size());
		assertEquals("Crate deposit 403033", actual.get(0));
	}

	@Test
	public void add_two_invalid_commands() {
		commandStorage.addInvalidCommand("Crate deposit 403033");
		commandStorage.addInvalidCommand("Crate savin 4030334 5403");
		List<String> actual = commandStorage.getInvalidCommands();
		assertEquals(2, actual.size());
		assertEquals("Crate deposit 403033", actual.get(0));
		assertEquals("Crate savin 4030334 5403", actual.get(1));
	}

	@Test
	public void add_three_invalid_commands() {
		commandStorage.addInvalidCommand("Crate deposit 403033");
		commandStorage.addInvalidCommand("Crate savin 4030334 5403");
		commandStorage.addInvalidCommand("deposit cd 4030334 5403");
		List<String> actual = commandStorage.getInvalidCommands();
		assertEquals(3, actual.size());
		assertEquals("Crate deposit 403033", actual.get(0));
		assertEquals("Crate savin 4030334 5403", actual.get(1));
		assertEquals("deposit cd 4030334 5403", actual.get(2));
	}

}
