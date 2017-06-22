package org.dialectic.jsonapi;

import static org.dialectic.jsonapi.Meta.ofEntry;
import static org.dialectic.jsonapi.links.ResourceLinks.related;

public class TestResourceWithRelationships implements ResourceWithRelationships {
    public String key = "value";

    @Override
    public Relationships getRelationships() {
        return new Relationships(
                Relationship.create(
                        "author", related("related")
                ).withResourceLinkage(
                        ResourceLinkage.nonEmptyToOne("reID", "reType")
                ).withMeta(
                        ofEntry("k", "v")
                )
        );
    }

    @Override
    public String getJsonApiDataId() {
        return "aID";
    }

    @Override
    public String getJsonApiDataType() {
        return "aType";
    }
}
