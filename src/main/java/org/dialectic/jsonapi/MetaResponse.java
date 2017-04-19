package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MetaResponse<T> implements JsonApiResponse {
    @JsonProperty
    private Object meta;

    @JsonCreator
    public MetaResponse(@JsonProperty("meta") Object meta) {
        this.meta = meta;
    }
}
