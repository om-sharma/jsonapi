package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Meta{
    @JsonProperty("meta")
    private Object metaObject;

    public Meta(Object metaObject) {
        this.metaObject = metaObject;
    }

}
