package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MetaReasponsTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Getter
    static class Sample {
        @JsonProperty
        String copyright = "value";
        @JsonProperty
        Map<String, String> aMap = new HashMap<String, String>() {
            {
                put("a", "b");
            }
        };
    }

    @Test
    public void metaResponse() throws JSONException, IOException {
        MetaResponse metaResponse = JsonApiResponse.metaResponse(new Meta(new Sample()));
        JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsBytes(metaResponse));

        JSONAssert.assertEquals("{'meta':{'copyright':'value', 'aMap': {'a':'b'}}}".replaceAll("'", "\""), jsonNode.toString(), true);

    }

}
