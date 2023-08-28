package io.apicurio.registry.rules;

import com.google.cloud.bigquery.Field;
import com.google.cloud.bigquery.FieldList;
import com.google.gson.JsonSyntaxException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BigqueryGsonBuilderTest {

    private final BigqueryGsonBuilder gsonBuilder = new BigqueryGsonBuilder();

    @Test
    public void testParse() {
        FieldList fieldList = gsonBuilder.parseFields("[{\"name\":\"id\",\"type\":\"STRING\",\"mode\":\"NULLABLE\"}]");
        assertEquals(Field.Mode.NULLABLE, fieldList.get(0).getMode());
    }

    @Test
    public void testParse_other_content() {
        FieldList fieldList = gsonBuilder.parseFields("[{\"name\":\"id\",\"type\":\"STRING\",\"count\":1}]");
        assertNull(fieldList.get(0).getMode());
    }

    @Test
    public void testParse_illegal_argument() {
        try {
            gsonBuilder.parseFields("[{\"name\":\"id\",\"type\":\"STRING\",\"mode\":\"NULLEABLE\"}]");
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("No enum constant com.google.cloud.bigquery.Field.Mode.NULLEABLE", e.getMessage());
        }
    }

    @Test
    public void testParse_no_json() {
        try {
            gsonBuilder.parseFields("phgp'çàh'nmslenb");
            fail("Exception expected");
        } catch (JsonSyntaxException e) {
            assertEquals("java.lang.IllegalStateException: Expected BEGIN_ARRAY but was STRING at line 1 column 1 path $", e.getMessage());
        }
    }
}