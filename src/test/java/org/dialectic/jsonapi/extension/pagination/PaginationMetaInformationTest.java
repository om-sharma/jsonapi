package org.dialectic.jsonapi.extension.pagination;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;

public class PaginationMetaInformationTest {
    @Test
    public void writesThePaginationBlock() throws IOException, JSONException {
        ObjectMapper objectMapper = new ObjectMapper();
        PaginationMetaInformation information = new PaginationMetaInformation(100, 10);
        JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsBytes(information));
        JSONAssert.assertEquals(jsonNode.toString(), "{'pagination':{'totalElements':100, 'totalPages': 10}}".replaceAll("'", "\""), true);
    }

}