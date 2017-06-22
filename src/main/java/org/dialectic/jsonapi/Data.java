package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.dialectic.jsonapi.links.ResourceLinks;

import static org.dialectic.jsonapi.JsonApiObjectMapper.objectMapper;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"id", "type", "attributes", "relationships", "links", "meta"})
public class Data<T> {
    @JsonProperty
    private String id;

    @JsonProperty
    private String type;

    private T attributes;

    @JsonProperty
    private Relationships relationships;

    @JsonProperty
    private ResourceLinks links;

    @JsonProperty
    private Meta meta;

    @JsonCreator
    public Data(@JsonProperty("id") String id,
                @JsonProperty("type") String type,
                @JsonProperty("attributes") T attributes,
                @JsonProperty("relationships") Relationships relationships,
                @JsonProperty("links") ResourceLinks links,
                @JsonProperty("meta") Meta meta) {
        this.attributes = attributes;
        this.relationships = relationships;
        this.id = id;
        this.type = type;
        this.links = links;
        this.meta = meta;
    }

    public String id() {
        return id;
    }

    public String type() {
        return type;
    }

    public T attributes() {
        return attributes;
    }

    public Relationships relationships() {
        return relationships;
    }

    @JsonProperty("attributes")
    private T writeAttributeObjectOnlyIfItIsNonEmpty() throws JsonProcessingException {
        if (objectMapper.writeValueAsString(attributes).matches("\\s*\\{\\s*\\}\\s*")) {
            return null;
        } else {
            return attributes;
        }
    }
}
