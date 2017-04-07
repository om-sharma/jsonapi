package org.dialectic.jsonapi.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@lombok.Data
@Builder
@AllArgsConstructor
public class ErrorSource{
    @JsonProperty("pointer")
    String pointer;

    @JsonProperty("parameter")
    String parameter;
}
