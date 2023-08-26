package io.apicurio.registry.rules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BigqueryGsonBuilderTest {

    @Test
    public void testParse() {
        BigqueryGsonBuilder gsonBuilder = new BigqueryGsonBuilder();
        try {
            gsonBuilder.parseFields("[{\"name\":\"id\",\"type\":\"STRING\",\"mode\":\"NULLEABLE\"}]");
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("No enum constant com.google.cloud.bigquery.Field.Mode.NULLEABLE", e.getMessage());
        }
    }
}