package org.dialectic.jsonapi;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;

public class MultiDataResponseTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void dataIsSerializedWithoutLinkWhenLinksAreEmpty() throws IOException, JSONException {
        MultiDataResponse<TestDataObject> multiDataResponse = new MultiDataResponse<>(new TestDataObject(99, "123"));
        JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsBytes(multiDataResponse));

        JSONAssert.assertEquals("{'data':[{'id':'99', 'type':'transaction','attributes':{'receiptNumber':'123'}}]}".replaceAll("'", "\""), jsonNode.toString(), true);
    }

    @Test
    public void dataIsSerializedWithLinkWhenLinksAreAvailable() throws IOException, JSONException {
        MultiDataResponse<TestDataObject> multiDataResponse = new MultiDataResponse<>(new TestDataObject(99, "123"));
        multiDataResponse.withLink(Link.builder().next("foo").build());
        JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsBytes(multiDataResponse));

        JSONAssert.assertEquals("{'data':[{'id':'99', 'type':'transaction','attributes':{'receiptNumber':'123'}}], 'links':{'next':'foo'}}".replaceAll("'", "\""), jsonNode.toString(), true);
    }

    @Test
    public void renderEmptyDataWhenNoDataIsAvailable() throws IOException, JSONException {
        MultiDataResponse<DataObject> multiDataResponse = new MultiDataResponse<>();
        JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsBytes(multiDataResponse));
        JSONAssert.assertEquals("{'data':[]}".replaceAll("'", "\""), jsonNode.toString(), true);
    }

}