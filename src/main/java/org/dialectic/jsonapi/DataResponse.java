package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.dialectic.jsonapi.links.PaginationLinks;
import org.dialectic.jsonapi.links.ResourceLinks;
import org.dialectic.jsonapi.links.ResponseLinks;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.dialectic.jsonapi.Meta.of;

@SuppressWarnings("unchecked")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"jsonapi", "data", "links", "meta", "included"})
public abstract class DataResponse<T extends Resource> implements JsonApiResponse {

    @JsonProperty
    private ResponseLinks links;

    @JsonProperty
    private Meta meta;

    @JsonProperty
    private Jsonapi jsonapi;

    @JsonProperty
    private List<Data> included;

    public DataResponse() {
    }

    public DataResponse(ResponseLinks links, Meta meta, Jsonapi jsonapi, List<Data> included) {
        this.links = links;
        this.meta = meta;
        this.jsonapi = jsonapi;
        this.included = included;
    }

    public <M extends DataResponse<T>, N extends Resource> M withIncludedResources(N... includedResources) {
        this.included = Arrays.stream(includedResources).map(this::serializeData).collect(Collectors.toList());
        return (M) this;
    }

    public <M extends DataResponse<T>> M withLinks(ResourceLinks resourceLinks, PaginationLinks paginationLinks) {
        this.links = new ResponseLinks(resourceLinks, paginationLinks);
        return (M) this;
    }

    public <M extends DataResponse<T>> M withLinks(ResourceLinks resourceLinks) {
        PaginationLinks paginationLinks = (links == null ? null : links.getPaginationLinks());
        return withLinks(resourceLinks, paginationLinks);
    }

    public <M extends DataResponse<T>> M withLinks(PaginationLinks paginationLinks) {
        ResourceLinks resourceLinks = (links == null ? null : links.getResourceLinks());
        return withLinks(resourceLinks, paginationLinks);
    }

    @Deprecated
    public <M extends DataResponse<T>> M withMeta(Object metaObject) {
        return withMeta(of(metaObject));
    }

    public <M extends DataResponse<T>> M withMeta(Meta metaObject) {
        return withMeta(metaObject, false);
    }

    public <M extends DataResponse<T>> M withMeta(Meta metaObject, boolean override) {
        if (meta == null || override) {
            meta = metaObject;
        } else {
            meta.merge(metaObject);
        }
        return (M) this;
    }

    public <M extends DataResponse<T>> M jsonapi(Jsonapi jsonapi) {
        this.jsonapi = jsonapi;
        return (M) this;
    }

    protected Data<T> serializeIntoDataBlock(T resource) {
        return (Data<T>) serializeData(resource);
    }

    private Data<Resource> serializeData(Resource resource) {
        Relationships relationships = (ResourceWithRelationships.class.isAssignableFrom(resource.getClass()))
                ? ((ResourceWithRelationships) resource).getRelationships()
                : null;

        return new Data(
                resource.getJsonApiDataId(),
                resource.getJsonApiDataType(),
                resource,
                relationships,
                resource.getLinks(),
                resource.getMeta()
        );
    }

    public List<Data> included() {
        return Collections.unmodifiableList(included);
    }
}

