package com.pheonix.axonevents.configs;

import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.DefaultMongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;

@Configuration
public class AggregateConfig {

	@Bean
	public EventStorageEngine eventStore(MongoClient client) {
		return new MongoEventStorageEngine(new DefaultMongoTemplate(client));
	}
}
