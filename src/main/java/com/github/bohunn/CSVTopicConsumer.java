package com.github.bohunn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohunn.model.IncomingRecordModel;
import com.github.bohunn.persistence.Person;
import com.github.bohunn.persistence.PersonRepository;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.hibernate.reactive.mutiny.Mutiny;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@Slf4j
public class CSVTopicConsumer {

    @Inject
    PersonRepository personRepository;

    @Inject
    Mutiny.SessionFactory sessionFactory;

    @Inject
    ObjectMapper objectMapper;

    @Incoming("csv-test")
//    public CompletionStage<Void> receive(Message<String> message) throws SystemException {
      public Uni<Void> receive(ConsumerRecord<String, String> message) throws JsonProcessingException {
        log.info("START - Received entry from Kafka:");
//        log.info("key: {}", message.key());
        log.info("key: {}, message: {}", message.key(), message.value());
//        log.info("key:{}, message: {}", message.getMetadata(), message.getPayload());


        IncomingRecordModel payload = objectMapper.readValue(message.value(), IncomingRecordModel.class);

        Person person = new Person(payload.getFirstName(), payload.getLastName(), payload.getEmail(), payload.getGender(), payload.getIpAddress());
        return sessionFactory.withTransaction((session, transaction) -> session.persist(person));
        //        try {
//            log.info("Transaction: {}", userTransaction.toString());
//            if (userTransaction.getStatus() == Status.STATUS_UNKNOWN) {
//                userTransaction.begin();
//            }
//        Uni<Person> object = personRepository.persistIncomingRecord(message.value());
//        log.info("object: {}", object);
//            personRepository.persistIncomingRecord(message.getPayload());
//        } catch (NotSupportedException exception) {
//            throw new RuntimeException(exception);
//        }
//        return message.ack();
    }
}
