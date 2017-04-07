package org.dialectic.jsonapi.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@Builder
@lombok.Data
public class ErrorLink{
    @JsonProperty("about")
    String about;
}
