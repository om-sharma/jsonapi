package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TestResource implements Resource {

    private final String id;
    private final String receiptNumber;

    @JsonCreator
    public TestResource(@JsonProperty("id") int id, @JsonProperty("receiptNumber")String receiptNumber) {
        this.id = String.valueOf(id);
        this.receiptNumber = receiptNumber;
    }

    @Override
    public String getJsonApiDataId() {
        return id;
    }

    @Override
    public String getJsonApiDataType() {
        return "transaction";
    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }
}
