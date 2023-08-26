package io.apicurio.registry.rules.validity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Validity {

    private String name;

    @JsonProperty("$comment")
    private String comment;

    private boolean passNone;

    private boolean passSyntax;

    private boolean passFull;

    private boolean skip;

    private JsonNode schemaJson;

    private String schemaNonJson;
}
