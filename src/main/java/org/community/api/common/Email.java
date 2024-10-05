package org.community.api.common;


import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.validator.routines.EmailValidator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = EmailDeserializer.class)
public record Email(String email) {
    private static final EmailValidator VALIDATOR = EmailValidator.getInstance(true, true);

    public Email {
        if (!VALIDATOR.isValid(email)) {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
    }

    @JsonValue
    public String value() {
        return email;
    }


}
