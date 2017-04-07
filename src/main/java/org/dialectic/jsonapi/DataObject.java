package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface DataObject {
    @JsonIgnore
    String getJsonApiDataId();

    @JsonIgnore
    String getJsonApiDataType();
}
