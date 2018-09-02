package com.pheonix.axonevents.commands;

public class DepositMoneyCommand extends BaseCommand<String> {

	public final double ammount;

	public DepositMoneyCommand(String id, double ammount) {
		super(id);
		this.ammount = ammount;
	}

}
