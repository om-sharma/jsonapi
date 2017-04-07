package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MetaResponse<T> implements JsonApiResponse {
    @JsonUnwrapped
    private Meta meta;

    public MetaResponse(Meta meta) {
        this.meta = meta;
    }
}
