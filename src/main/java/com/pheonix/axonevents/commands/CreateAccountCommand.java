package com.pheonix.axonevents.commands;

public class CreateAccountCommand extends BaseCommand<String>{

	public final String accountCreator;

	public CreateAccountCommand(String id, String accountCreator) {
		super(id);
		this.accountCreator = accountCreator;
	}

}
