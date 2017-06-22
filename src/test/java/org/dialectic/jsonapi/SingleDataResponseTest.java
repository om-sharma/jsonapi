package org.dialectic.jsonapi;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.dialectic.jsonapi.links.ResourceLinks;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;

import static org.dialectic.jsonapi.Meta.ofEntry;
import static org.dialectic.jsonapi.links.PaginationLinks.links;
import static org.dialectic.jsonapi.testsupport.TestSupport.*;

public class SingleDataResponseTest {
    @Test
    public void dataIsSerializedWithoutLinkWhenLinksAreEmpty() throws IOException, JSONException {
        SingleDataResponse<TestResource> singleDataResponse = new SingleDataResponse<>(new TestResource(88, "123"));
        JsonNode jsonNode = toJsonNode(singleDataResponse);

        JSONAssert.assertEquals(jString("{'data':{'id':'88', 'type':'transaction','attributes':{'receiptNumber':'123'}}}"), jsonNode.toString(), true);
    }

    @Test
    public void responseWithMetaAndJsonapi() throws IOException, JSONException {
        SingleDataResponse<TestResource> singleDataResponse = new SingleDataResponse<>(new TestResource(88, "123"))
                .withMeta(ofEntry("a", "b"))
                .jsonapi(Jsonapi.builder().version("1.0").meta(ofEntry("a", "b")).build());
        JsonNode jsonNode = toJsonNode(singleDataResponse);
        JSONAssert.assertEquals(jString("{'data':{'id':'88', 'type':'transaction','attributes':{'receiptNumber':'123'}}, 'meta':{'a':'b'}, 'jsonapi': {'version':'1.0', 'meta':{'a':'b'}}}"), jsonNode.toString(), true);
    }

    @Test
    public void dataIsSerializedWithLinkWhenLinksAreAvailable() throws IOException, JSONException {
        SingleDataResponse<TestResource> singleDataResponse = new SingleDataResponse<>(new TestResource(88, "123"));
        singleDataResponse.withLinks(links(null, null, "foo", null));

        assertJsonEquals(jString("{'data':{'id':'88', 'type':'transaction','attributes':{'receiptNumber':'123'}}, 'links':{'next':'foo'}}"), toJsonString(singleDataResponse));

        singleDataResponse.withLinks(ResourceLinks.links("selfLink", "relatedLink"));

        assertJsonEquals(jString("{'data':{'id':'88', 'type':'transaction','attributes':{'receiptNumber':'123'}}, 'links':{'next':'foo', 'self':'selfLink', 'related':'relatedLink'}}"), toJsonString(singleDataResponse));
    }

    @Test
    public void serializeAndThenDeserializeAgain() throws IOException, JSONException {

        SingleDataResponse<TestResource> singleDataResponse = new SingleDataResponse<>(new TestResource(88, "123"))
                .withMeta(ofEntry("a", "b"))
                .jsonapi(Jsonapi.builder().version("1.0").meta(ofEntry("a", "b")).build());

        JsonNode jsonNode = toJsonNode(singleDataResponse);

        SingleDataResponse<TestResource> deserializeDataResponse = objectMapper.readValue(jsonNode.toString(), new TypeReference<SingleDataResponse<TestResource>>(){});

        JSONAssert.assertEquals(toJsonNode(deserializeDataResponse).toString(), jsonNode.toString(), true);

    }
}