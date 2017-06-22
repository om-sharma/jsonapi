package org.dialectic.jsonapi.links;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.IOException;

import static org.dialectic.jsonapi.links.Link.StringLink;
import static org.dialectic.jsonapi.links.LinkCreator.create;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@EqualsAndHashCode
public class ResourceLinks {
    @JsonProperty("self")
    private Link self;

    @JsonProperty("related")
    private Link related;

    @JsonCreator
    private static ResourceLinks deserialize(@JsonProperty("self") Object self, @JsonProperty("related") Object related) throws IOException {
        return ResourceLinks.links(create(self), create(related));
    }

    private ResourceLinks(Link self, Link related) {
        this.self = self;
        this.related = related;
    }

    public static ResourceLinks self(String self) {
        return new ResourceLinks(new StringLink(self), null);
    }

    public static ResourceLinks self(Link self) {
        return new ResourceLinks(self, null);
    }

    public static ResourceLinks related(String related) {
        return new ResourceLinks(null, new StringLink(related));
    }

    public static ResourceLinks related(Link related) {
        return new ResourceLinks(null, related);
    }

    public static ResourceLinks links(String self, String related) {
        return new ResourceLinks(new StringLink(self), new StringLink(related));
    }

    public static ResourceLinks links(Link self, String related) {
        return new ResourceLinks(self, new StringLink(related));
    }

    public static ResourceLinks links(String self, Link related) {
        return new ResourceLinks(new StringLink(self), related);
    }

    public static ResourceLinks links(Link self, Link related) {
        return new ResourceLinks(self, related);
    }
}

