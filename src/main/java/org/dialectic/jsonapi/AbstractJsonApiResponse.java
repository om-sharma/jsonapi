package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonInclude;

@SuppressWarnings("unchecked")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class AbstractJsonApiResponse<T extends DataObject> implements JsonApiResponse {
    private Links links;

    public <M extends AbstractJsonApiResponse> M withLink(Links links) {
        this.links = links;
        return (M) this;
    }

    public Links getLinks() {
        return links;
    }
}
