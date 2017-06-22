package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.dialectic.jsonapi.JsonApiObjectMapper.readToClass;


public interface ResourceLinkage {
    static ResourceLinkage createResourceLinkage(Object data) throws IOException {
        if (data == null) return new SingleResourceLinkage(null);

        Class<? extends ResourceLinkage> resourceLinkageClass = List.class.isAssignableFrom(data.getClass()) ? ManyResourceLinkage.class : SingleResourceLinkage.class;
        return readToClass(data, resourceLinkageClass);
    }

    static SingleResourceLinkage emptyToOne() {
        return new SingleResourceLinkage(null);
    }

    static SingleResourceLinkage nonEmptyToOne(String id, String type) {
        return new SingleResourceLinkage(ResourceIdentifier.of(id, type));
    }

    static ManyResourceLinkage emptyToMany() {
        return new ManyResourceLinkage(Collections.emptyList());
    }

    static ManyResourceLinkage nonEmptyToMany(ResourceIdentifier... resourceIdentifiers) {
        //Todo: add emptyCheck
        return new ManyResourceLinkage(Arrays.asList(resourceIdentifiers));
    }

    @EqualsAndHashCode
    class SingleResourceLinkage implements ResourceLinkage {

        @JsonProperty("data")
        public final ResourceIdentifier resourceIdentifier;

        @JsonCreator
        public SingleResourceLinkage(ResourceIdentifier resourceIdentifier) {
            this.resourceIdentifier = resourceIdentifier;
        }
    }

    @EqualsAndHashCode
    class ManyResourceLinkage implements ResourceLinkage {

        @JsonProperty("data")
        public final Collection<ResourceIdentifier> resourceIdentifier;

        @JsonCreator
        public ManyResourceLinkage(Collection<ResourceIdentifier> resourceIdentifier) {
            this.resourceIdentifier = resourceIdentifier;
        }
    }
}

