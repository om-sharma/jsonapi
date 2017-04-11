package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Links {
    String self;
    String related;
    String first;
    String last;
    String prev;
    String next;
}
