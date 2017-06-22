package org.dialectic.jsonapi;

import org.dialectic.jsonapi.links.ResourceLinks;
import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;

import static org.dialectic.jsonapi.Meta.ofEntry;
import static org.dialectic.jsonapi.testsupport.TestSupport.*;
import static org.junit.Assert.assertEquals;

public class RelationshipsTest {
    @Test
    public void relationshipAreSerializedCorrectly() throws IOException, JSONException {
        Relationships relationships = new Relationships(
                Relationship.create("author", ResourceLinks.self("/articles/1/relationships/author")),
                Relationship.create("comments", ofEntry("metaKey", "metaValue")),
                Relationship.create("publisher_1", ResourceLinkage.emptyToOne()).withLinks(ResourceLinks.self("self-link")),
                Relationship.create("publisher_2", ResourceLinkage.emptyToMany()).withMeta(ofEntry("k", "v")),
                Relationship.create("publisher_3", ResourceLinkage.nonEmptyToOne("id3", "atype")),
                Relationship.create("publisher_4", ResourceLinkage.nonEmptyToMany(new ResourceIdentifier("id4", "atype")))
        );

        String jsonString = toJsonString(relationships);

        assertJsonEquals(jString("{" +
                "'author': {'links':{'self':'/articles/1/relationships/author'}}," +
                "'comments': {'meta':{'metaKey':'metaValue'}}," +
                "'publisher_1': {'data':null, 'links':{'self':'self-link'}}," +
                "'publisher_2': {'data':[], 'meta':{'k':'v'}}," +
                "'publisher_3': {'data':{'id':'id3','type':'atype'}}," +
                "'publisher_4': {'data':[{'id':'id4','type':'atype'}]}" +
                "}"), jsonString);

        Relationships deserializedRelationships = objectMapper.readValue(jsonString, Relationships.class);

        assertEquals(relationships, deserializedRelationships);
        assertJsonEquals(jsonString, toJsonString(deserializedRelationships));
    }
}