package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Account {

	private double balance;
	private double apr;
	private int id;
	private String accountType;
	private int monthsSinceCreation;
	private boolean withdrawnThisMonth;
	private List<String> transationHistory;

	public Account(double apr, int id) {
		this.apr = apr;
		this.id = id;
		this.monthsSinceCreation = 0;
		this.withdrawnThisMonth = false;
		this.transationHistory = new ArrayList<>();
	}

	public double getApr() {
		return apr;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public void deposit(double amount) {
		this.balance += amount;
	}

	public void withdraw(double amount) {
		this.balance -= amount;

		if (this.balance <= 0) {
			this.balance = 0;
		}

		this.withdrawnThisMonth = true;
	}

	public void incrementMonth() {
		this.monthsSinceCreation += 1;
		this.withdrawnThisMonth = false;
	}

	public void addCommand(String command) {
		transationHistory.add(command);
	}

	public List<String> getTransationHistory() {
		return transationHistory;
	}

	public String getAccountState() {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		decimalFormat.setRoundingMode(RoundingMode.FLOOR);

		String type = this.getAccountType();
		String outputId = Integer.toString(this.getId());
		String outputBalance = decimalFormat.format(this.getBalance());
		String outoutApr = decimalFormat.format(this.getApr());

		return type + " " + outputId + " " + outputBalance + " " + outoutApr;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getId() {
		return this.id;
	}

	public int getMonthsSinceCreation() {
		return this.monthsSinceCreation;
	}

	public boolean getWithDrawnThisMonth() {
		return withdrawnThisMonth;
	}
}
