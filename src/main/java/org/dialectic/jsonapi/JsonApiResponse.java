package org.dialectic.jsonapi;

import org.dialectic.jsonapi.error.ErrorResponse;
import org.dialectic.jsonapi.error.Error;

import java.util.List;

@SuppressWarnings("unchecked")
public interface JsonApiResponse {
    static <T extends DataObject> SingleDataResponse<T> singleDataResponse(T data) {
        //todo: add null check
        return new SingleDataResponse<>(data);
    }

    static <T extends DataObject> MultiDataResponse<T> multiDataResponse(List<T> data) {
        return new MultiDataResponse<>(data);
    }

    static <T extends Error> ErrorResponse<T> errorResponse(T... errors) {
        return new ErrorResponse<>(errors);
    }

    static MetaResponse metaResponse(Object meta) {
        return new MetaResponse(meta);
    }
}
