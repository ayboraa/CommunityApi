package org.community.api.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.UUID;

public class PostIdDeserializer extends JsonDeserializer<PostId> {
    @Override
    public PostId deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        String uuidString = jsonParser.getText();
        return new PostId(UUID.fromString(uuidString));
    }
}