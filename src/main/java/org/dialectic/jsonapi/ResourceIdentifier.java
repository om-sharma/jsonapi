package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ResourceIdentifier{
    public final String id;
    public final String type;

    @JsonCreator
    public ResourceIdentifier(@JsonProperty("id") String id, @JsonProperty("type") String type ) {
        this.id = id;
        this.type = type;
    }

    public static ResourceIdentifier of(String id, String type){
        return new ResourceIdentifier(id, type);
    }
}
