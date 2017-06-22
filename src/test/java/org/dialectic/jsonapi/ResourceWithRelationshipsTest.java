package org.dialectic.jsonapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.junit.Test;

import java.util.Collections;

import static org.dialectic.jsonapi.testsupport.TestSupport.*;

public class ResourceWithRelationshipsTest {
    @Test
    public void relationshipsAreAlwaysWrittenWithInSingleDataWhenPresent() throws JsonProcessingException, JSONException {
        SingleDataResponse<TestResourceWithRelationships> singleDataResponse = JsonApiResponse.singleDataResponse(new TestResourceWithRelationships());

        assertJsonEquals(
                jString("{'data':{'id':'aID','type':'aType','attributes':{'key':'value'},'relationships':{'author': {'data': {'id':'reID','type':'reType'},'meta':{'k':'v'}, 'links':{'related':'related'}}}}}"),
                objectMapper.writeValueAsString(singleDataResponse));
    }

    @Test
    public void relationshipsAreAlwaysWrittenWithInMultipleDataWhenPresent() throws JsonProcessingException, JSONException {
        MultiDataResponse<TestResourceWithRelationships> multiple = JsonApiResponse.multiDataResponse(Collections.singletonList(new TestResourceWithRelationships()));

        assertJsonEquals(
                jString("{'data':[{'id':'aID','type':'aType','attributes':{'key':'value'},'relationships':{'author':{'data': {'id':'reID','type':'reType'},'meta':{'k':'v'}, 'links':{'related':'related'}}}}]}"),
                objectMapper.writeValueAsString(multiple));
    }


}
