package org.dialectic.jsonapi;

import com.fasterxml.jackson.annotation.JsonIgnore;

class TestDataObject implements DataObject {

    private final String id;
    private final String receiptNumber;

    public TestDataObject(int id, String receiptNumber) {
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
