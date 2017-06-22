package org.dialectic.jsonapi.links;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.dialectic.jsonapi.JsonApiObjectMapper.readToClass;
import static org.dialectic.jsonapi.links.LinkCreator.create;

@SuppressWarnings({"unchecked", "unused"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseLinks {
    private ResourceLinks resourceLinks;
    private PaginationLinks paginationLinks;

    public ResponseLinks(ResourceLinks resourceLinks, PaginationLinks paginationLinks) {
        this.resourceLinks = resourceLinks;
        this.paginationLinks = paginationLinks;
    }

    public ResourceLinks getResourceLinks() {
        return resourceLinks;
    }

    public PaginationLinks getPaginationLinks() {
        return paginationLinks;
    }

    @JsonCreator
    private static ResponseLinks serialize(@JsonProperty("self") Object self,
                                           @JsonProperty("related") Object related,
                                           @JsonProperty("first") Object first,
                                           @JsonProperty("prev") Object previous,
                                           @JsonProperty("next") Object next,
                                           @JsonProperty("last") Object last) {
        return new ResponseLinks(
                ResourceLinks.links(create(self), create(related)),
                PaginationLinks.links(create(first), create(last), create(next), create(previous))
        );
    }

    @JsonValue
    private Map<String, Link> links() {
        Map<String, Link> links = new HashMap<>();

        Map<String, Link> rlinks = readToClass(resourceLinks, Map.class);
        if (rlinks != null) {
            links.putAll(rlinks);
        }

        Map<String, Link> plinks = readToClass(paginationLinks, Map.class);
        if (plinks != null) {
            links.putAll(plinks);
        }

        return links.entrySet().stream().filter(e -> e.getValue() != null).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
