package org.dialectic.jsonapi.error;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dialectic.jsonapi.JsonApiResponse;
import org.dialectic.jsonapi.Meta;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

public class ErrorResponseTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void errorResponse() throws IOException, JSONException {
        Error error1 = Error.builder()
                .applicationErrorCode("A123")
                .httpStatusCode("400")
                .detail("Some detail")
                .id("Error_1")
                .links(new org.dialectic.jsonapi.error.ErrorLink("about value"))
                .source(new ErrorSource("pointer", "parameter"))
                .title("some title")
                .build();
        Error error2 = Error.builder()
                .applicationErrorCode("B123")
                .httpStatusCode("200")
                .detail("Some other detail")
                .id("Error_2")
                .links(new org.dialectic.jsonapi.error.ErrorLink("about value 1"))
                .source(new ErrorSource("pointer_1", "parameter_1"))
                .meta(new HashMap<String, String>() {{
                          put("key", "value");
                      }}
                )
                .title("some other title")
                .build();
        ErrorResponse<Error> errorResponse = JsonApiResponse.errorResponse(error1, error2);

        JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsBytes(errorResponse));

        String expected = "{'errors':[" +
                "{'id':'Error_1', 'links': {'about':'about value'}, 'status':'400', 'code':'A123', 'title':'some title', 'detail':'Some detail', 'source': {'pointer':'pointer','parameter':'parameter'}}," +
                "{'id':'Error_2', 'links': {'about':'about value 1'}, 'status':'200', 'code':'B123', 'title':'some other title', 'detail':'Some other detail', 'source': {'pointer':'pointer_1','parameter':'parameter_1'},'meta':{'key':'value'}}," +
                "]}"
                        .replaceAll("'", "\"");

        JSONAssert.assertEquals(expected, jsonNode.toString(), true);
    }

    @Test
    public void errorResponseWithMeta() throws JSONException, IOException {
        Error error = Error.builder()
                .title("some title")
                .build();

        ErrorResponse<Error> errorResponse = JsonApiResponse.errorResponse(error).withMeta(new Meta(Collections.singletonMap("a", "b")));
        JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsBytes(errorResponse));

        String expected = "{'errors':[{'title':'some title'}], 'meta':{'a':'b'}}"
                        .replaceAll("'", "\"");

        JSONAssert.assertEquals(expected, jsonNode.toString(), true);
    }
}
