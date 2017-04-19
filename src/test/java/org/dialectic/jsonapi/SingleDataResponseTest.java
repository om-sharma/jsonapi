package org.dialectic.jsonapi;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.util.Collections;

public class SingleDataResponseTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void dataIsSerializedWithoutLinkWhenLinksAreEmpty() throws IOException, JSONException {
        SingleDataResponse<TestDataObject> singleDataResponse = new SingleDataResponse<>(new TestDataObject(88, "123"));
        JsonNode jsonNode = getJsonNode(singleDataResponse);

        JSONAssert.assertEquals("{'data':{'id':'88', 'type':'transaction','attributes':{'receiptNumber':'123'}}}".replaceAll("'", "\""), jsonNode.toString(), true);
    }

    @Test
    public void responseWithMetaAndJsonapi() throws IOException, JSONException {
        SingleDataResponse<TestDataObject> singleDataResponse = new SingleDataResponse<>(new TestDataObject(88, "123"))
                .withMeta(Collections.singletonMap("a", "b"))
                .jsonapi(Jsonapi.builder().version("1.0").meta(Collections.singletonMap("a", "b")).build());
        JsonNode jsonNode = getJsonNode(singleDataResponse);
        JSONAssert.assertEquals("{'data':{'id':'88', 'type':'transaction','attributes':{'receiptNumber':'123'}}, 'meta':{'a':'b'}, 'jsonapi': {'version':'1.0', 'meta':{'a':'b'}}}".replaceAll("'", "\""), jsonNode.toString(), true);
    }

    @Test
    public void dataIsSerializedWithLinkWhenLinksAreAvailable() throws IOException, JSONException {
        SingleDataResponse<TestDataObject> singleDataResponse = new SingleDataResponse<>(new TestDataObject(88, "123"));
        singleDataResponse.withLinks(Links.builder().next("foo").build());
        JsonNode jsonNode = getJsonNode(singleDataResponse);

        JSONAssert.assertEquals("{'data':{'id':'88', 'type':'transaction','attributes':{'receiptNumber':'123'}}, 'links':{'next':'foo'}}".replaceAll("'", "\""), jsonNode.toString(), true);
    }

    @Test
    public void serializeAndThenDeserializeAgain() throws IOException, JSONException {

        SingleDataResponse<TestDataObject> singleDataResponse = new SingleDataResponse<>(new TestDataObject(88, "123"))
                .withMeta(Collections.singletonMap("a", "b"))
                .jsonapi(Jsonapi.builder().version("1.0").meta(Collections.singletonMap("a", "b")).build());

        JsonNode jsonNode = getJsonNode(singleDataResponse);

        SingleDataResponse<TestDataObject> deserializeDataResponse = objectMapper.readValue(jsonNode.toString(), new TypeReference<SingleDataResponse<TestDataObject>>(){});

        JSONAssert.assertEquals(getJsonNode(deserializeDataResponse).toString(), jsonNode.toString(), true);

    }

    private JsonNode getJsonNode(SingleDataResponse<TestDataObject> singleDataResponse) throws IOException {
        return objectMapper.readTree(objectMapper.writeValueAsBytes(singleDataResponse));
    }


}