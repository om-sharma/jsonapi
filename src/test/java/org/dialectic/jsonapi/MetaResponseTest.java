package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.dialectic.jsonapi.Meta.of;
import static org.dialectic.jsonapi.testsupport.TestSupport.*;

public class MetaResponseTest {
    static class Sample {
        @JsonProperty
        private String copyright = "value";
        @JsonProperty
        private Map<String, String> aMap = new HashMap<String, String>() {
            {
                put("a", "b");
            }
        };

        public Sample() {
        }

        @JsonCreator
        public Sample(@JsonProperty("copyright") String copyright, @JsonProperty("aMap") Map<String, String> aMap) {
            this.copyright = copyright;
            this.aMap = aMap;
        }
    }

    @Test
    public void metaResponse() throws JSONException, IOException {
        MetaResponse metaResponse = JsonApiResponse.metaResponse(of(new Sample()));
        JsonNode jsonNode = toJsonNode(metaResponse);

        JSONAssert.assertEquals(jString("{'meta':{'copyright':'value', 'aMap': {'a':'b'}}}"), jsonNode.toString(), true);

    }

    @Test
    public void serializeAndThenDeserializeAgain() throws IOException, JSONException {

        MetaResponse metaResponse = JsonApiResponse.metaResponse(of(new Sample()));
        JsonNode jsonNode = toJsonNode(metaResponse);

        MetaResponse<Sample> deserializeMultiDataResponse = objectMapper.readValue(jsonNode.toString(), new TypeReference<MetaResponse<Sample>>() {
        });

        JSONAssert.assertEquals(toJsonNode(deserializeMultiDataResponse).toString(), jsonNode.toString(), true);

    }
}
