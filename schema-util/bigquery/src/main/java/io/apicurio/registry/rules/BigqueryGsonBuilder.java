package io.apicurio.registry.rules;

import com.google.cloud.bigquery.Field;
import com.google.cloud.bigquery.FieldList;
import com.google.cloud.bigquery.LegacySQLTypeName;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Arrays;

public class BigqueryGsonBuilder {

    private final GsonBuilder gsonBuilder;

    public BigqueryGsonBuilder() {
        gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(LegacySQLTypeName.class, new LegacySQLTypeNameAdapter(false))
                .registerTypeAdapter(FieldList.class, new FieldListAdapter())
                .setFieldNamingStrategy(field -> {
                    if (field.getName().equals("subFields")) {
                        return "fields";
                    }
                    return field.getName();
                });
        //validationBuilder = new GsonBuilder()
    }

    public void validateSyntax(String content) {
        Object o = new GsonBuilder().create().fromJson(content, Object.class);
        if (o instanceof String) throw new JsonSyntaxException("Object expected");
    }

    public FieldList parseFields(String content) {
        Field[] fields = gsonBuilder.create().fromJson(content, Field[].class);
        Arrays.stream(fields).forEach(Field::getMode);
        return FieldList.of(fields);
    }

    private static class LegacySQLTypeNameAdapter extends TypeAdapter<LegacySQLTypeName> {

        private final boolean lenient;

        public LegacySQLTypeNameAdapter(boolean lenient) {
            this.lenient = lenient;
        }

        @Override
        public void write(JsonWriter jsonWriter, LegacySQLTypeName legacySQLTypeName) throws IOException {
            jsonWriter.value(legacySQLTypeName.name());
        }

        @Override
        public LegacySQLTypeName read(JsonReader jsonReader) throws IOException {
            if (lenient) {
                try {
                    return LegacySQLTypeName.valueOf(jsonReader.nextString());
                } catch(IllegalArgumentException e) {
                    return LegacySQLTypeName.BYTES;
                }
            }
            return LegacySQLTypeName.valueOfStrict(jsonReader.nextString());
        }
    }

    private class FieldListAdapter extends TypeAdapter<FieldList> {

        @Override
        public void write(JsonWriter jsonWriter, FieldList fields) throws IOException {
            jsonWriter.value(gsonBuilder.create().toJson(fields));
        }

        @Override
        public FieldList read(JsonReader jsonReader) {
            Gson gson = gsonBuilder.create();
            Field[] fields = gson.fromJson(jsonReader, Field[].class);
            return FieldList.of(fields);
        }
    }
}
