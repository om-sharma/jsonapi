package org.dialectic.jsonapi;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@SuppressWarnings("WeakerAccess")
@JsonSerialize
public class SingleDataResponse<T extends DataObject> extends DataResponse<T> {
    private Data<T> data;

    public SingleDataResponse(T data) {
        this.data = Data.with(data.getJsonApiDataId(), data.getJsonApiDataType(), data);
    }

    public Data<T> getData() {
        return data;
    }
}


