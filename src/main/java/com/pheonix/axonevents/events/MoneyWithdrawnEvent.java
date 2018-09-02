package com.pheonix.axonevents.events;

public class MoneyWithdrawnEvent extends BaseEvent<String> {

	public final double amount;

	public MoneyWithdrawnEvent(String id, double amount) {
		super(id);
		this.amount = amount;
	}

}
