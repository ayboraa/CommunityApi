package org.community.api.common;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.util.Assert;

import java.util.UUID;

@JsonDeserialize(using = CommentIdDeserializer.class)
public record CommentId(UUID uuid) {

    public CommentId {

        Assert.notNull(uuid, "Id cannot be null");

    }

    public CommentId(){

        this(UUID.randomUUID());

    }

    @JsonValue
    public String value() {
        return uuid.toString();
    }

}
