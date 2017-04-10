package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;

@SuppressWarnings("unchecked")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
public abstract class DataResponse<T extends DataObject> implements JsonApiResponse {
    @JsonProperty
    private Link links;

    @JsonUnwrapped
    private Meta meta;

    @JsonProperty
    private Jsonapi jsonapi;

    public <M extends DataResponse<T>> M withLink(Link link) {
        this.links = link;
        return (M) this;
    }

    public <M extends DataResponse<T>> M withMeta(Meta meta) {
        this.meta = meta;
        return (M) this;
    }

    public <M extends DataResponse<T>> M jsonapi(Jsonapi jsonapi){
        this.jsonapi = jsonapi;
        return (M) this;
    }
}

