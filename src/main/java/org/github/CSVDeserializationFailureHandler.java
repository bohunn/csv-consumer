package org.github;

import io.smallrye.reactive.messaging.kafka.DeserializationFailureHandler;
import org.apache.kafka.common.header.Headers;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@ApplicationScoped
@Named("failure-fallback")
public  class CSVDeserializationFailureHandler implements DeserializationFailureHandler<Value> {
    static Value fallback = new Value("fallback","fallback","fallback","fallback","fallback","fallback");

    @Override
    public Value handleDeserializationFailure(String topic, boolean isKey, String deserializer, byte[] data, Exception exception, Headers headers) {
        return fallback;
    }

}
