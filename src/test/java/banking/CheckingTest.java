package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CheckingTest {

	final int DEFAULT_APR = 8;
	final int DEFAULT_ID = 89456185;

	@Test
	public void balance_starts_at_0() {
		Checking checking = new Checking(DEFAULT_APR, DEFAULT_ID);
		double actual = checking.getBalance();

		assertEquals(0, actual);
	}

}
