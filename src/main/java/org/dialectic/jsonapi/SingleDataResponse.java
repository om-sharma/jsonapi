package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.dialectic.jsonapi.links.ResponseLinks;

import java.util.List;

@SuppressWarnings("WeakerAccess")
@JsonSerialize
public class SingleDataResponse<T extends Resource> extends DataResponse<T> {
    @JsonProperty
    private Data<T> data;

    @JsonCreator
    public SingleDataResponse(@JsonProperty("links") ResponseLinks links,
                              @JsonProperty("meta") Meta meta,
                              @JsonProperty("jsonapi") Jsonapi jsonapi,
                              @JsonProperty("data") Data<T> data,
                              @JsonProperty("included") List<Data> included
    ) {
        super(links, meta, jsonapi, included);
        this.data = data;
    }

    public SingleDataResponse(T data) {
        this.data = serializeIntoDataBlock(data);
    }

    public Data<T> data() {
        return data;
    }
}


