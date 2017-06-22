package org.dialectic.jsonapi.extension.pagination;

import org.json.JSONException;
import org.junit.Test;

import java.io.IOException;

import static org.dialectic.jsonapi.testsupport.TestSupport.jString;
import static org.dialectic.jsonapi.testsupport.TestSupport.toJsonString;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class PaginationMetaInformationTest {
    @Test
    public void writesThePaginationBlock() throws IOException, JSONException {
        PaginationMetaInformation information = new PaginationMetaInformation(100, 10);
        assertEquals(toJsonString(information), jString("{'pagination':{'totalElements':100, 'totalPages': 10}}"), true);
    }

}