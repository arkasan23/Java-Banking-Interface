package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CDTest {

	final int DEFAULT_APR = 8;
	final int DEFAULT_ID = 89456185;

	@Test
	public void starting_balance_is_supplied_balance() {
		CD cd = new CD(DEFAULT_APR, 500, DEFAULT_ID);
		double actual = cd.getBalance();

		assertEquals(500, actual);
	}

}
