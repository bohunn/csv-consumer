package com.github.bohunn.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohunn.model.IncomingRecordModel;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import java.util.UUID;

@Slf4j
@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person> {

    @Inject
    ObjectMapper objectMapper;

    @ReactiveTransactional
//    @Transactional
    @Cascade(CascadeType.PERSIST)
    public Uni<Person> persistIncomingRecord(String record) {
        log.info("persistIncomingRecord: {}", record);
        try {
            IncomingRecordModel payload = objectMapper.readValue(record, IncomingRecordModel.class);

            Person person = new Person(payload.getFirstName(), payload.getLastName(), payload.getEmail(), payload.getGender(), payload.getIpAddress());
            log.info("person: {}", person);

            return persistAndFlush(person)
                    .onFailure(PersistenceException.class)
                    .recoverWithItem(() -> {
                        log.error("Unable to create the parameter");
                        return null;
                    });

        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }

    }

    public Uni<Person> findById(UUID uuid) {
        return find("id", uuid).firstResult();
    }
}
