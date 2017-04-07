package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonInclude;

@SuppressWarnings("unchecked")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public abstract class AbstractJsonApiResponse<T extends DataObject> implements JsonApiResponse {
    private Link links;

    public <M extends AbstractJsonApiResponse> M withLink(Link link) {
        this.links = link;
        return (M) this;
    }

    public Link getLinks() {
        return links;
    }
}
