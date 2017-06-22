package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.dialectic.jsonapi.links.ResponseLinks;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@SuppressWarnings({"WeakerAccess", "unchecked", "unused"})
@JsonSerialize
@EqualsAndHashCode(callSuper = true)
@ToString
public class MultiDataResponse<T extends Resource> extends DataResponse<T> {
    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private List<Data<T>> datas;

    @JsonCreator
    public MultiDataResponse(
            @JsonProperty("links") ResponseLinks links,
            @JsonProperty("meta") Meta meta,
            @JsonProperty("jsonapi") Jsonapi jsonapi,
            @JsonProperty("data") List<Data<T>> datas,
            @JsonProperty("included") List<Data> included
    ) {
        super(links, meta, jsonapi, included);
        this.datas = datas;
    }

    public MultiDataResponse(List<T> data) {
        this.datas = data.stream().map(this::serializeIntoDataBlock).collect(Collectors.toList());
    }

    public MultiDataResponse(T... data) {
        this(asList(data));
    }

    public List<Data<T>> data() {
        return Collections.unmodifiableList(datas);
    }
}
