package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;

@SuppressWarnings("unchecked")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
public abstract class AbstractJsonApiResponse<T extends DataObject> implements JsonApiResponse {
    @JsonProperty
    private Link links;

    @JsonUnwrapped
    private Meta meta;

    public <M extends AbstractJsonApiResponse> M withLink(Link link) {
        this.links = link;
        return (M) this;
    }

    public <N extends AbstractJsonApiResponse> N withMeta(Meta meta) {
        this.meta = meta;
        return (N) this;
    }
}
