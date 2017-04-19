package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@SuppressWarnings("WeakerAccess")
@JsonSerialize
public class SingleDataResponse<T extends DataObject> extends DataResponse<T> {
    private Data<T> data;

    @JsonCreator
    public SingleDataResponse(@JsonProperty("links") Links links, @JsonProperty("meta") Object meta, @JsonProperty("jsonapi") Jsonapi jsonapi, @JsonProperty("data") Data<T> data) {
        super(links, meta, jsonapi);
        this.data = data;
    }

    public SingleDataResponse(T data) {
        this.data = Data.with(data.getJsonApiDataId(), data.getJsonApiDataType(), data);
    }

    public Data<T> getData() {
        return data;
    }
}


