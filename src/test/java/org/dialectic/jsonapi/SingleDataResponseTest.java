package org.dialectic.jsonapi;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;

public class SingleDataResponseTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void dataIsSerializedWithoutLinkWhenLinksAreEmpty() throws IOException, JSONException {
        SingleDataResponse<TestDataObject> singleDataResponse = new SingleDataResponse<>(new TestDataObject(88, "123"));
        JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsBytes(singleDataResponse));

        JSONAssert.assertEquals("{'data':{'id':'88', 'type':'transaction','attributes':{'receiptNumber':'123'}}}".replaceAll("'", "\""), jsonNode.toString(), true);
    }

    @Test
    public void dataIsSerializedWithLinkWhenLinksAreAvailable() throws IOException, JSONException {
        SingleDataResponse<TestDataObject> singleDataResponse = new SingleDataResponse<>(new TestDataObject(88, "123"));
        singleDataResponse.withLink(Link.builder().next("foo").build());
        JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsBytes(singleDataResponse));

        JSONAssert.assertEquals("{'data':{'id':'88', 'type':'transaction','attributes':{'receiptNumber':'123'}}, 'links':{'next':'foo'}}".replaceAll("'", "\""), jsonNode.toString(), true);
    }

}