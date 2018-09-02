package com.pheonix.axonevents.commands;

public class WithdrawMoneyCommand extends BaseCommand<String> {

	public final double amount;

	public WithdrawMoneyCommand(String id, double amount) {
		super(id);
		this.amount = amount;
	}

}
