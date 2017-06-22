package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ResourceWithRelationships extends Resource {
    @JsonIgnore
    Relationships getRelationships();
}
