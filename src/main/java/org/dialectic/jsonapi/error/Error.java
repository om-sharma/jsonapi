package org.dialectic.jsonapi.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class Error {
    @JsonProperty("id")
    String id;

    @JsonProperty("links")
    ErrorLink links;

    @JsonProperty("status")
    String httpStatusCode;

    @JsonProperty("code")
    String applicationErrorCode;

    @JsonProperty("title")
    String title;

    @JsonProperty("detail")
    String detail;

    @JsonProperty("source")
    ErrorSource source;

    //todo: this must be an object which can be serialized in json (try enforcing it)
    @JsonProperty("meta")
    private Object meta;
}

