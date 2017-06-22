package org.dialectic.jsonapi;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.dialectic.jsonapi.Meta.ofEntry;
import static org.dialectic.jsonapi.links.PaginationLinks.links;
import static org.dialectic.jsonapi.testsupport.TestSupport.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class MultiDataResponseTest {
    @Test
    public void dataIsSerializedWithoutLinkWhenLinksAreEmpty() throws IOException, JSONException {
        MultiDataResponse<TestResource> multiDataResponse = new MultiDataResponse<>(new TestResource(99, "123"));
        JsonNode jsonNode = toJsonNode(multiDataResponse);

        assertEquals(jString("{'data':[{'id':'99', 'type':'transaction','attributes':{'receiptNumber':'123'}}]}"), jsonNode.toString(), true);
    }

    @Test
    public void dataIsSerializedWithLinkWhenLinksAreAvailable() throws IOException, JSONException {
        MultiDataResponse<TestResource> multiDataResponse = new MultiDataResponse<>(new TestResource(99, "123"));
        multiDataResponse.withLinks(links(null, null, "foo", null));

        assertEquals(jString("{'data':[{'id':'99', 'type':'transaction','attributes':{'receiptNumber':'123'}}], 'links':{'next':'foo'}}"), toJsonString(multiDataResponse), true);
    }

    @Test
    public void renderEmptyDataWhenNoDataIsAvailable() throws IOException, JSONException {
        MultiDataResponse<Resource> multiDataResponse = new MultiDataResponse<>();
        assertEquals(jString("{'data':[]}"), toJsonString(multiDataResponse), true);
    }


    @Test
    public void multiDataResponseWithMeta() throws IOException, JSONException {
        MultiDataResponse<TestResource> multiDataResponse = new MultiDataResponse<>(new TestResource(99, "123"))
                .withMeta(ofEntry("a", "b"))
                .withMeta(ofEntry("c", "d"));

        assertEquals(jString("{'data':[{'id':'99', 'type':'transaction','attributes':{'receiptNumber':'123'}}], 'meta':{'a':'b', 'c':'d'}}"), toJsonString(multiDataResponse), true);

        multiDataResponse.withMeta(ofEntry("k", "v"), true);

        assertEquals(jString("{'data':[{'id':'99', 'type':'transaction','attributes':{'receiptNumber':'123'}}], 'meta':{'k':'v'}}"), toJsonString(multiDataResponse), true);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void serializeAndThenDeserializeAgain() throws IOException, JSONException {

        MultiDataResponse<TestResource> multiDataResponse = new MultiDataResponse<>(
                new TestResource(99, "123"),
                new TestResource(98, null)
        ).withLinks(
                links("foo", null, null, null)
        ).withMeta(
                ofEntry("a", "b")
        );

        JsonNode jsonNode = toJsonNode(multiDataResponse);

        Map<String, List<Map<String, ?>>> jsonNode1 = toMap(multiDataResponse);

        assertThat(jsonNode1.get("data").get(0).containsKey("attributes"), is(true));
        assertThat(jsonNode1.get("data").get(1).containsKey("attributes"), is(false));

        MultiDataResponse<TestResource> deserializeMultiDataResponse = objectMapper.readValue(jsonNode.toString(), new TypeReference<MultiDataResponse<TestResource>>() {
        });

        assertJsonEquals(toJsonNode(deserializeMultiDataResponse).toString(), jsonNode.toString());

    }
}