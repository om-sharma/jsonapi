package org.dialectic.jsonapi;

import com.fasterxml.jackson.databind.JsonNode;
import org.dialectic.jsonapi.links.Link;
import org.dialectic.jsonapi.links.ResourceLinks;
import org.junit.Test;

import java.io.IOException;

import static org.dialectic.jsonapi.testsupport.TestSupport.*;

public class ResourceLinksTest {
    @Test
    public void createResourceLinks() {
        ResourceLinks relationships = ResourceLinks.links("selfLink", "relatedLink");
        assertJsonEquals(jString("{'self':'selfLink','related':'relatedLink'}"), toJsonString(relationships));
    }

    @Test
    public void createOnlySelfLink() {
        ResourceLinks relationships = ResourceLinks.self("selfLink");
        assertJsonEquals(jString("{'self':'selfLink'}"), toJsonString(relationships));
    }

    @Test
    public void createOnlyRelatedLink() {
        ResourceLinks relationships = ResourceLinks.related("relatedLink");
        assertJsonEquals(jString("{'related':'relatedLink'}"), toJsonString(relationships));
    }

    @Test
    public void createOnlySelfLinkWithLinkObject() {
        ResourceLinks relationships = ResourceLinks.self(new Link.ObjectLink("selfLink"));
        assertJsonEquals(jString("{'self':{'href':'selfLink'}}"), toJsonString(relationships));
    }

    @Test
    public void createOnlyRelatedLinkWithLinkObject() {
        ResourceLinks relationships = ResourceLinks.related(new Link.ObjectLink("relatedLink"));
        assertJsonEquals(jString("{'related':{'href':'relatedLink'}}"), toJsonString(relationships));
    }


    @Test
    public void deserializeBackAgain() throws IOException {

        ResourceLinks relationships = ResourceLinks.links("selfLink", new Link.ObjectLink("relatedLink"));
        JsonNode jsonNode = toJsonNode(relationships);

        JsonNode deserialized = toJsonNode(objectMapper.readValue(jsonNode.toString(), ResourceLinks.class));

        assertJsonEquals(jsonNode.toString(), deserialized.toString());
    }


}