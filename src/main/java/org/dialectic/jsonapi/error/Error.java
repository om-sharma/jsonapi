package org.dialectic.jsonapi.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dialectic.jsonapi.Meta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@EqualsAndHashCode
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

    @JsonProperty("meta")
    private Meta meta;
}

