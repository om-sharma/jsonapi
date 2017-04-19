package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class Jsonapi {

    @JsonCreator
    public Jsonapi(@JsonProperty("version") String version, @JsonProperty("meta") Object meta) {
        this.version = version;
        this.meta = meta;
    }

    @JsonProperty
    private String version;

    @JsonProperty
    private Object meta;
}
