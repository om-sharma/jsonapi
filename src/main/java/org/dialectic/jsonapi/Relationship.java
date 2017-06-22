package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.*;
import org.dialectic.jsonapi.links.ResourceLinks;
import lombok.EqualsAndHashCode;

import java.io.IOException;
import java.util.Map;

import static org.dialectic.jsonapi.JsonApiObjectMapper.readToClass;
import static org.dialectic.jsonapi.ResourceLinkage.createResourceLinkage;

//Todo: must verify that at least one of the element present as per spec
@SuppressWarnings({"SameParameterValue", "WeakerAccess", "FieldCanBeLocal", "unused"})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"data", "links", "meta"})
@EqualsAndHashCode
public class Relationship {
    @JsonUnwrapped
    private ResourceLinkage resourceLinkage;

    @JsonProperty("links")
    private ResourceLinks resourceLinks;

    @JsonProperty
    private Meta meta;

    @JsonIgnore
    private String resourceType;

    @JsonIgnore
    public String getResourceType() {
        return resourceType;
    }

    void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Relationship withLinks(ResourceLinks resourceLinks) {
        this.resourceLinks = resourceLinks;
        return this;
    }

    public Relationship withMeta(Meta metaObject) {
        return withMeta(metaObject, false);
    }

    public Relationship withMeta(Meta metaObject, boolean override) {
        if (meta == null || override) {
            meta = metaObject;
        } else {
            meta.merge(metaObject);
        }
        return this;
    }

    public Relationship withResourceLinkage(ResourceLinkage resourceLinkage) {
        this.resourceLinkage = resourceLinkage;
        return this;
    }

    public static Relationship create(String resourceType, ResourceLinkage resourceLinkage) {
        return aRelationship(resourceType).withResourceLinkage(resourceLinkage);
    }

    public static Relationship create(String resourceType, ResourceLinks resourceLinks) {
        return aRelationship(resourceType).withLinks(resourceLinks);
    }

    public static Relationship create(String resourceType, Meta meta) {
        return aRelationship(resourceType).withMeta(meta);
    }

    private static Relationship aRelationship(String resourceType) {
        Relationship relationship = new Relationship();
        //Todo: verify that resourcetype is never null
        relationship.resourceType = resourceType;
        return relationship;
    }

    @JsonCreator
    private static Relationship deserialize(Map<String, ?> relationshipMap) throws IOException {
        Relationship relationship = new Relationship();

        relationship.meta = readToClass(relationshipMap.get("meta"), Meta.class);

        relationship.resourceLinks = readToClass(relationshipMap.get("links"), ResourceLinks.class);

        if (relationshipMap.containsKey("data")) {
            relationship.resourceLinkage = createResourceLinkage(relationshipMap.get("data"));
        }

        return relationship;
    }

}
