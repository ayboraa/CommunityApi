package org.community.api.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class EmailDeserializer extends JsonDeserializer<Email> {
    @Override
    public Email deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        String emailString = jsonParser.getText();
        return new Email(emailString);
    }
}
