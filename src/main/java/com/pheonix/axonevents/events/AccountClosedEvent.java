package com.pheonix.axonevents.events;

import org.springframework.util.Assert;

public class AccountClosedEvent {

	public final String id;

	public AccountClosedEvent(String id) {
		Assert.notNull(id, "Id cannot be null");
		this.id = id;
	}

}
