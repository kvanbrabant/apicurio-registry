package io.apicurio.registry.rules.compatibility;

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
        ComparableSchema proposed = toSchema(proposedArtifact);
        Set<CompatibilityDifference> differences = new HashSet<>();
        proposed.checkCompatibilityWith(toSchema(existing), differences);
        return differences;
    }

    @Override
    protected CompatibilityDifference transform(CompatibilityDifference original) {
        return original;
    }

    private ComparableSchema toSchema(String content) {
        return new ComparableSchema(bigqueryGsonBuilder.parseFields(content));
    }

}
