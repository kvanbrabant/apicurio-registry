package io.apicurio.registry.rules.compatibility;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.apicurio.registry.content.ContentHandle;
import io.apicurio.registry.rules.BigqueryGsonBuilder;

import java.util.*;

public class BigqueryCompatibilityChecker extends AbstractCompatibilityChecker<CompatibilityDifference> {

    private final static BigqueryGsonBuilder bigqueryGsonBuilder;

    static {
        bigqueryGsonBuilder = new BigqueryGsonBuilder();
    }

    @Override
    protected Set<CompatibilityDifference> isBackwardsCompatibleWith(String existing, String proposedArtifact,
                                                                                   Map<String, ContentHandle> resolvedReferences) {
        try {
            ComparableSchema proposed = toSchema(proposedArtifact);
            Set<CompatibilityDifference> differences = new HashSet<>();
            proposed.checkCompatibilityWith(toSchema(existing), differences);
            return differences;
        } catch (JsonProcessingException e) {
            return Collections.singleton(
                    new BigquerySchemaCompatibilityDifference(e.getMessage(), e.getLocation().toString())
            );
        }

    }

    @Override
    protected CompatibilityDifference transform(CompatibilityDifference original) {
        return original;
    }

    private ComparableSchema toSchema(String content) throws JsonProcessingException {
        return new ComparableSchema(bigqueryGsonBuilder.parseFields(content));
    }

}
