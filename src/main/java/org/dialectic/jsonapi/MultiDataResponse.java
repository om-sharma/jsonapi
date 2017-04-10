package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"WeakerAccess", "unchecked"})
@JsonSerialize
public class MultiDataResponse<T extends DataObject> extends DataResponse<T> {
    private List<Data<T>> datas;

    public MultiDataResponse(List<T> data) {
        this.datas = data.stream().map(d -> Data.with(d.getJsonApiDataId(), d.getJsonApiDataType(), d)).collect(Collectors.toList());
    }

    public MultiDataResponse(T... data) {
        this(Arrays.asList(data));
    }

    @JsonInclude(JsonInclude.Include.ALWAYS)
    public List<Data<T>> getData() {
        return datas;
    }
}
