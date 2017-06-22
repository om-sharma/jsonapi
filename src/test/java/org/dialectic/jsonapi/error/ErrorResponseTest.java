package org.dialectic.jsonapi.error;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.dialectic.jsonapi.JsonApiResponse;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.util.Arrays;

import static org.dialectic.jsonapi.Meta.ofEntry;
import static org.dialectic.jsonapi.testsupport.TestSupport.*;

public class ErrorResponseTest {
    @Test
    public void errorResponse() throws IOException, JSONException {
        Error error1 = Error.builder()
                .applicationErrorCode("A123")
                .httpStatusCode("400")
                .detail("Some detail")
                .id("Error_1")
                .links(new ErrorLink("about value"))
                .source(new ErrorSource("pointer", "parameter"))
                .title("some title")
                .build();
        Error error2 = Error.builder()
                .applicationErrorCode("B123")
                .httpStatusCode("200")
                .detail("Some other detail")
                .id("Error_2")
                .links(new ErrorLink("about value 1"))
                .source(new ErrorSource("pointer_1", "parameter_1"))
                .meta(ofEntry("key", "value"))
                .title("some other title")
                .build();
        ErrorResponse<Error> errorResponse = JsonApiResponse.errorResponse(error1, error2);

        JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsBytes(errorResponse));

        String expected = jString("{'errors':[" +
                "{'id':'Error_1', 'links': {'about':'about value'}, 'status':'400', 'code':'A123', 'title':'some title', 'detail':'Some detail', 'source': {'pointer':'pointer','parameter':'parameter'}}," +
                "{'id':'Error_2', 'links': {'about':'about value 1'}, 'status':'200', 'code':'B123', 'title':'some other title', 'detail':'Some other detail', 'source': {'pointer':'pointer_1','parameter':'parameter_1'},'meta':{'key':'value'}}," +
                "]}");

        JSONAssert.assertEquals(expected, jsonNode.toString(), true);

        errorResponse = JsonApiResponse.errorResponse(Arrays.asList(error1, error2));
        jsonNode = objectMapper.readTree(objectMapper.writeValueAsBytes(errorResponse));
        JSONAssert.assertEquals(expected, jsonNode.toString(), true);
    }

    @Test
    public void errorResponseWithMeta() throws JSONException, IOException {
        Error error = Error.builder()
                .title("some title")
                .build();

        ErrorResponse<Error> errorResponse = JsonApiResponse.errorResponse(error)
                .withMeta(ofEntry("a", "b"))
                .withMeta(ofEntry("c", "d"));


        JSONAssert.assertEquals(jString("{'errors':[{'title':'some title'}], 'meta':{'a':'b', 'c':'d'}}"), toJsonString(errorResponse), true);

        errorResponse.withMeta(ofEntry("m", "n"), true);

        JSONAssert.assertEquals(jString("{'errors':[{'title':'some title'}], 'meta':{'m':'n'}}"), toJsonString(errorResponse), true);
    }

    @Test
    public void serializeAndThenDeserializeAgain() throws IOException, JSONException {

        Error error = Error.builder()
                .title("some title")
                .build();

        ErrorResponse<Error> errorResponse = JsonApiResponse.errorResponse(error).withMeta(ofEntry("a", "b"));

        JsonNode jsonNode = toJsonNode(errorResponse);

        ErrorResponse<Error> deserializeMultiDataResponse = objectMapper.readValue(jsonNode.toString(), new TypeReference<ErrorResponse<Error>>() {
        });

        JSONAssert.assertEquals(toJsonNode(deserializeMultiDataResponse).toString(), jsonNode.toString(), true);

    }
}
