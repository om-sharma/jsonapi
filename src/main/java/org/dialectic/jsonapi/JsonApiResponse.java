package org.dialectic.jsonapi;

import org.dialectic.jsonapi.error.Error;
import org.dialectic.jsonapi.error.ErrorResponse;

import java.util.List;

@SuppressWarnings("unchecked")
public interface JsonApiResponse {
    static <T extends Resource> SingleDataResponse<T> singleDataResponse(T data) {
        //todo: add null check
        return new SingleDataResponse<>(data);
    }

    static <T extends Resource> MultiDataResponse<T> multiDataResponse(List<T> data) {
        return new MultiDataResponse<>(data);
    }

    static <T extends Error> ErrorResponse<T> errorResponse(T... errors) {
        return new ErrorResponse<>(errors);
    }

    static <T extends Error> ErrorResponse<T> errorResponse(List<T> errors) {
        return new ErrorResponse(errors);
    }

    static MetaResponse metaResponse(Meta meta) {
        return new MetaResponse(meta);
    }
}
