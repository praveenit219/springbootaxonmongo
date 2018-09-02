package com.pheonix.axonevents.events;

public class MoneyDepositedEvent extends BaseEvent<String> {
	
	public final double amount;

	public MoneyDepositedEvent(String id, double amount) {
		super(id);
		this.amount = amount;
	}

}
