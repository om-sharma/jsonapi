package org.dialectic.jsonapi;

import org.dialectic.jsonapi.error.ErrorResponse;
import org.dialectic.jsonapi.error.Error;

import java.util.List;

@SuppressWarnings("unchecked")
public interface JsonApiResponse {
    static <T extends DataObject> SingleDataResponse<T> withData(T data) {
        //todo: add null check
        return new SingleDataResponse<>(data);
    }

    static <T extends DataObject> MultiDataResponse<T> withData(List<T> data) {
        return new MultiDataResponse<>(data);
    }

    static <T extends Error> ErrorResponse<T> withErrors(T... errors) {
        return new ErrorResponse<>(errors);
    }
}
