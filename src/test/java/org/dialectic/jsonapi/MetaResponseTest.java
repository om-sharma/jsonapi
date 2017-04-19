package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MetaResponseTest {
    private ObjectMapper objectMapper = new ObjectMapper();

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
        MetaResponse metaResponse = JsonApiResponse.metaResponse(new Sample());
        JsonNode jsonNode = getJsonNode(metaResponse);

        JSONAssert.assertEquals("{'meta':{'copyright':'value', 'aMap': {'a':'b'}}}".replaceAll("'", "\""), jsonNode.toString(), true);

    }

    @Test
    public void serializeAndThenDeserializeAgain() throws IOException, JSONException {

        MetaResponse metaResponse = JsonApiResponse.metaResponse(new Sample());
        JsonNode jsonNode = getJsonNode(metaResponse);

        MetaResponse<Sample> deserializeMultiDataResponse = objectMapper.readValue(jsonNode.toString(), new TypeReference<MetaResponse<Sample>>() {
        });

        JSONAssert.assertEquals(getJsonNode(deserializeMultiDataResponse).toString(), jsonNode.toString(), true);

    }

    private JsonNode getJsonNode(MetaResponse metaResponse) throws IOException {
        return objectMapper.readTree(objectMapper.writeValueAsBytes(metaResponse));
    }

}
