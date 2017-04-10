package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;

@Builder
public class Jsonapi {
    @JsonProperty
    private String version;

    @JsonUnwrapped
    private Meta meta;
}
