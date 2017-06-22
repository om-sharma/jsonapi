package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.dialectic.jsonapi.links.ResourceLinks;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface Resource extends Serializable {
    @JsonIgnore
    String getJsonApiDataId();

    @JsonIgnore
    String getJsonApiDataType();

    @JsonIgnore
    default ResourceLinks getLinks() {
        return null;
    }

    @JsonIgnore
    default Meta getMeta() {
        return null;
    }
}
