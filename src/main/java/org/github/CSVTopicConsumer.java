package org.github;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.record.Record;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
@Slf4j
public class CSVTopicConsumer {

    @Incoming("csv-test")
    public CompletionStage<Void> receive(Message<Record> message) {
//        var metadata = message.getMetadata(IncomingKafkaRecordMetadata.class).orElseThrow();
        log.info("START - Received entry from Kafka:");
        log.info("message: {}", message);
        log.info("payload: {}", message.getPayload());
        //        log.info("got metadata: {}", metadata.getTopic());
//        log.info("message payload: {} ", message.getPayload());
        log.info("END");
        return message.ack();
    }
}
