package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@SuppressWarnings("unchecked")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter
public abstract class DataResponse<T extends DataObject> implements JsonApiResponse {
    @JsonProperty
    private Links links;

    @JsonProperty("meta")
    private Object meta;

    @JsonProperty
    private Jsonapi jsonapi;

    public DataResponse() {
    }

    public DataResponse(Links links, Object meta, Jsonapi jsonapi) {
        this.links = links;
        this.meta = meta;
        this.jsonapi = jsonapi;
    }

    public <M extends DataResponse<T>> M withLinks(Links links) {
        this.links = links;
        return (M) this;
    }

    public <M extends DataResponse<T>> M withMeta(Object metaObject) {
        this.meta = metaObject;
        return (M) this;
    }

    public <M extends DataResponse<T>> M jsonapi(Jsonapi jsonapi){
        this.jsonapi = jsonapi;
        return (M) this;
    }
}

