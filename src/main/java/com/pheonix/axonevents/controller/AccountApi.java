package com.pheonix.axonevents.controller;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.model.AggregateNotFoundException;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pheonix.axonevents.commands.CloseAccountCommand;
import com.pheonix.axonevents.commands.CreateAccountCommand;
import com.pheonix.axonevents.commands.DepositMoneyCommand;
import com.pheonix.axonevents.commands.WithdrawMoneyCommand;
import com.pheonix.axonevents.exceptions.InsufficientBalanceException;

@RequestMapping("/accounts")
@RestController
public class AccountApi {

	private final CommandGateway commandGateway;

	private final EventStore eventStore;

	public AccountApi(CommandGateway commandGateway, EventStore eventStore) {
		this.commandGateway = commandGateway;
		this.eventStore = eventStore;
	}

	static class AccountOwner {
		public String name;
	}


	@GetMapping("{id}/events")
	public List<Object> getEvents(@PathVariable String id) {
		return eventStore.readEvents(id).asStream().map(s -> s.getPayload()).collect(Collectors.toList());
	}

	@PostMapping
	public CompletableFuture<String> createAccount(@RequestBody AccountOwner user) {
		String id = UUID.randomUUID().toString();
		return commandGateway.send(new CreateAccountCommand(id, user.name));
	}

	@PutMapping(path = "{accountId}/balance")
	public CompletableFuture<String> deposit(@RequestBody double ammount, @PathVariable String accountId) {
		if (ammount > 0) {
			return commandGateway.send(new DepositMoneyCommand(accountId, ammount));
		} else {
			return commandGateway.send(new WithdrawMoneyCommand(accountId, -ammount));
		}
	}

	@DeleteMapping("{id}")
	public CompletableFuture<String> delete(@PathVariable String id) {
		return commandGateway.send(new CloseAccountCommand(id));
	}

	@ExceptionHandler(AggregateNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void notFound() {
	}

	@ExceptionHandler(InsufficientBalanceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String insufficientBalance(InsufficientBalanceException exception) {
		return exception.getMessage();
	}
}
