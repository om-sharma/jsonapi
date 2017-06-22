package org.dialectic.jsonapi.error;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.dialectic.jsonapi.JsonApiResponse;
import org.dialectic.jsonapi.Meta;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unchecked"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"errors", "meta"})
@Getter
@EqualsAndHashCode
@ToString
public class ErrorResponse<T extends Error> implements JsonApiResponse {
    @JsonProperty
    private List<T> errors;

    @JsonProperty
    private Meta meta;

    @JsonCreator
    public ErrorResponse(@JsonProperty("errors") List<T> errors, @JsonProperty("meta") Meta meta) {
        this.errors = errors;
        this.meta = meta;
    }

    public ErrorResponse(List<T> errors) {
        this.errors = errors;
    }

    public ErrorResponse(T... errors) {
        this(Arrays.asList(errors));
    }

    public ErrorResponse<T> withMeta(Meta metaObject) {
        return withMeta(metaObject, false);
    }

    public ErrorResponse<T> withMeta(Meta metaObject, boolean override) {
        if (meta == null || override) {
            meta = metaObject;
        } else {
            meta.merge(metaObject);
        }
        return this;
    }
}

