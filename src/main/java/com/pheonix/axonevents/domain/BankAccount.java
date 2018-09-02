package com.pheonix.axonevents.domain;

import java.io.Serializable;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import com.pheonix.axonevents.commands.CloseAccountCommand;
import com.pheonix.axonevents.commands.CreateAccountCommand;
import com.pheonix.axonevents.commands.DepositMoneyCommand;
import com.pheonix.axonevents.commands.WithdrawMoneyCommand;
import com.pheonix.axonevents.events.AccountClosedEvent;
import com.pheonix.axonevents.events.AccountCreatedEvent;
import com.pheonix.axonevents.events.MoneyDepositedEvent;
import com.pheonix.axonevents.events.MoneyWithdrawnEvent;
import com.pheonix.axonevents.exceptions.InsufficientBalanceException;

@Aggregate
public class BankAccount implements Serializable {


	private static final long serialVersionUID = 1L;

	@AggregateIdentifier
	private String id;

	private double balance;

	private String owner;

	@CommandHandler
	public BankAccount(CreateAccountCommand command) {
		String id = command.id;
		String name = command.accountCreator;

		Assert.hasLength(id, "Missing id");
		Assert.hasLength(name, "Missig account creator");

		AggregateLifecycle.apply(new AccountCreatedEvent(id, name, 0));
	}

	public BankAccount() {
		// constructor needed for reconstruction
	}

	@CommandHandler
	protected void on(CloseAccountCommand command) {
		AggregateLifecycle.apply(new AccountClosedEvent(id));
	}

	@CommandHandler
	protected void on(DepositMoneyCommand command) {
		double ammount = command.ammount;

		Assert.isTrue(ammount > 0.0, "Deposit must be a positive number.");

		AggregateLifecycle.apply(new MoneyDepositedEvent(id, ammount));
	}

	@CommandHandler
	protected void on(WithdrawMoneyCommand command) {
		double amount = command.amount;

		Assert.isTrue(amount > 0.0, "Withdraw must be a positive number.");

		if(balance - amount < 0) {
			throw new InsufficientBalanceException("Insufficient balance. Trying to withdraw: " + amount + ", but current balance is: " + balance);
		}
		AggregateLifecycle.apply(new MoneyWithdrawnEvent(id, amount));
	}

	@EventSourcingHandler
	protected void on(AccountCreatedEvent event) {
		this.id = event.id;
		this.owner = event.accountCreator;
		this.balance = event.balance;
	}

	@EventSourcingHandler
	protected void on(AccountClosedEvent event) {
		AggregateLifecycle.markDeleted();
	}

	@EventSourcingHandler
	protected void on(MoneyDepositedEvent event) {
		this.balance += event.amount;
	}

	@EventSourcingHandler
	protected void on(MoneyWithdrawnEvent event) {
		this.balance -= event.amount;
	}

	public double getBalance() {
		return balance;
	}

}
