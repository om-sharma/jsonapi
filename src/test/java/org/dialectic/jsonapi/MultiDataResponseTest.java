package org.dialectic.jsonapi;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

public class MultiDataResponseTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void dataIsSerializedWithoutLinkWhenLinksAreEmpty() throws IOException, JSONException {
        MultiDataResponse<TestDataObject> multiDataResponse = new MultiDataResponse<>(new TestDataObject(99, "123"));
        JsonNode jsonNode = getJsonNode(multiDataResponse);

        JSONAssert.assertEquals("{'data':[{'id':'99', 'type':'transaction','attributes':{'receiptNumber':'123'}}]}".replaceAll("'", "\""), jsonNode.toString(), true);
    }

    @Test
    public void dataIsSerializedWithLinkWhenLinksAreAvailable() throws IOException, JSONException {
        MultiDataResponse<TestDataObject> multiDataResponse = new MultiDataResponse<>(new TestDataObject(99, "123"));
        multiDataResponse.withLinks(Links.builder().next("foo").build());
        JsonNode jsonNode = getJsonNode(multiDataResponse);

        JSONAssert.assertEquals("{'data':[{'id':'99', 'type':'transaction','attributes':{'receiptNumber':'123'}}], 'links':{'next':'foo'}}".replaceAll("'", "\""), jsonNode.toString(), true);
    }

    @Test
    public void renderEmptyDataWhenNoDataIsAvailable() throws IOException, JSONException {
        MultiDataResponse<DataObject> multiDataResponse = new MultiDataResponse<>();
        JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsBytes(multiDataResponse));
        JSONAssert.assertEquals("{'data':[]}".replaceAll("'", "\""), jsonNode.toString(), true);
    }


    @Test
    public void multiDataResponseWithMeta() throws IOException, JSONException {
        MultiDataResponse<TestDataObject> multiDataResponse = new MultiDataResponse<>(new TestDataObject(99, "123")).withMeta(Collections.singletonMap("a", "b"));
        JsonNode jsonNode = getJsonNode(multiDataResponse);

        JSONAssert.assertEquals("{'data':[{'id':'99', 'type':'transaction','attributes':{'receiptNumber':'123'}}], 'meta':{'a':'b'}}".replaceAll("'", "\""), jsonNode.toString(), true);
    }

    @Test
    public void serializeAndThenDeserializeAgain() throws IOException, JSONException {

        MultiDataResponse<TestDataObject> multiDataResponse =
                new MultiDataResponse<>(new TestDataObject(99, "123"))
                        .withLinks(Links.builder().first("foo").build())
                        .withMeta(new HashMap<String, String>() {{
                                      put("a", "b");
                                  }}
                        );

        JsonNode jsonNode = getJsonNode(multiDataResponse);

        MultiDataResponse<TestDataObject> deserializeMultiDataResponse = objectMapper.readValue(jsonNode.toString(), new TypeReference<MultiDataResponse<TestDataObject>>(){});

        JSONAssert.assertEquals(getJsonNode(deserializeMultiDataResponse).toString(), jsonNode.toString(), true);

    }

    private JsonNode getJsonNode(MultiDataResponse<TestDataObject> multiDataResponse) throws IOException {
        return objectMapper.readTree(objectMapper.writeValueAsBytes(multiDataResponse));
    }
}