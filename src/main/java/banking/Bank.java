package banking;

import java.util.LinkedHashMap;
import java.util.Map;

public class Bank {
	private Map<Object, Account> accounts;

	public Bank() {
		accounts = new LinkedHashMap<Object, Account>();
	}

	public Map<Object, Account> getAccounts() {
		return accounts;
	}

	public void addAccount(Account account) {
		accounts.put(account.getId(), account);
	}

	public void removeAccount(Account account) {
		accounts.remove(account.getId());
	}

	public Account retrieveAccount(int id) {
		return accounts.get(id);
	}

	public void depositByID(int id, double amount) {
		accounts.get(id).deposit(amount);
	}

	public void withdrawByID(int id, double amount) {
		accounts.get(id).withdraw(amount);
	}
}
