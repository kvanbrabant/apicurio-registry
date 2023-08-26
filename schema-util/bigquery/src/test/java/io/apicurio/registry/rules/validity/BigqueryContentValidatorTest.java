package io.apicurio.registry.rules.validity;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.apicurio.registry.content.ContentHandle;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.fail;

class BigqueryContentValidatorTest {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final BigqueryContentValidator validator = new BigqueryContentValidator();

    private static final ObjectMapper MAPPER = new ObjectMapper();


    private String readFile(String path) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) return null;
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    @Test
    public void testValidity() throws IOException {
        ValidityList testCases = MAPPER.readValue(getClass().getClassLoader().getResourceAsStream("validity.json"), ValidityList.class);
        // Add cases that are not JSON
        testCases.getTestCases().add(Validity.builder()
                .name("case-100")
                .passNone(true)
                .passSyntax(false)
                .passFull(false)
                .schemaNonJson(readFile("notajson-1.txt"))
                .build());

        for (Validity testCase : testCases.getTestCases()) {
            if (testCase.isSkip()) {
                log.warn("Skipping ignored test case {}", testCase.getName());
                continue;
            }
            ContentHandle content = null;
            if (testCase.getSchemaJson() != null) {
                content = ContentHandle.create(MAPPER.writeValueAsString(testCase.getSchemaJson()));
            } else if (testCase.getSchemaNonJson() != null) {
                content = ContentHandle.create(testCase.getSchemaNonJson());
            } else {
                fail("TODO");
            }
            boolean pass;
            try {
                validator.validate(ValidityLevel.NONE, content, Collections.emptyMap());
                pass = true;
            } catch (Exception ex) {
                pass = false;
            }
            if (pass != testCase.isPassNone()) {
                fail("Test case " + testCase.getName() + " fails check for validity level NONE. Expected " + testCase.isPassNone() + " actual " + pass);
            }
            try {
                validator.validate(ValidityLevel.SYNTAX_ONLY, content, Collections.emptyMap());
                pass = true;
            } catch (Exception ex) {
                pass = false;
            }
            if (pass != testCase.isPassSyntax()) {
                fail("Test case " + testCase.getName() + " fails check for validity level SYNTAX. Expected " + testCase.isPassSyntax() + " actual " + pass);
            }
            try {
                validator.validate(ValidityLevel.FULL, content, Collections.emptyMap());
                pass = true;
            } catch (Exception ex) {
                pass = false;
            }
            if (pass != testCase.isPassFull()) {
                fail("Test case " + testCase.getName() + " fails check for validity level FULL. Expected " + testCase.isPassFull() + " actual " + pass);
            }
        }
    }
}
