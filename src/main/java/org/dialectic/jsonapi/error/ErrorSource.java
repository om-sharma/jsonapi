package org.dialectic.jsonapi.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@lombok.Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class ErrorSource{
    @JsonProperty("pointer")
    String pointer;

    @JsonProperty("parameter")
    String parameter;
}
