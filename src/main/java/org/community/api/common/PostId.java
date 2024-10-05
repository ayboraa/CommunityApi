package org.community.api.common;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.util.Assert;

import java.util.UUID;

@JsonDeserialize(using = MemberIdDeserializer.class)
public record PostId(UUID uuid) {

    public PostId {
        Assert.notNull(uuid, "Id cannot be null");
    }

    public PostId(){
        this(UUID.randomUUID());
    }


    @JsonValue
    public String value() {
        return uuid.toString();
    }


}

