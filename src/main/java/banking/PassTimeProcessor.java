package banking;

import java.util.Iterator;

public class PassTimeProcessor {

	private Bank bank;

	public PassTimeProcessor(Bank bank) {
		this.bank = bank;

	}

	private void calculateAPR(Account account) {
		double apr = account.getApr() / 100;
		double montly = apr / 12;
		double amountToAdd = account.getBalance() * montly;
		account.deposit(amountToAdd);
	}

	private void calcuateCDAPR(Account account) {
		double apr = account.getApr() / 100;
		double monthly = apr / 12;

		for (int i = 0; i < 4; i++) {
			double amountToAdd = account.getBalance() * monthly;
			account.deposit(amountToAdd);
		}
	}

	public void processCommand(String[] commandParsed) {
		int amountOfMonths = Integer.parseInt(commandParsed[1]);

		for (int i = 0; i < amountOfMonths; i++) {

			Iterator<Account> accounts = bank.getAccounts().values().iterator();
			while (accounts.hasNext()) {
				Account account = accounts.next();

				if (account.getBalance() == 0) {
					accounts.remove();
				}

				if (account.getBalance() < 100) {
					account.withdraw(25);
				}

				if (account.getAccountType().equals("Cd")) {
					calcuateCDAPR(account);
				} else {
					calculateAPR(account);
				}

				account.incrementMonth();

			}

		}

	}

}
