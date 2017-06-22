package org.dialectic.jsonapi.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Builder
@lombok.Data
@EqualsAndHashCode
public class ErrorLink{
    @JsonProperty("about")
    String about;
}
