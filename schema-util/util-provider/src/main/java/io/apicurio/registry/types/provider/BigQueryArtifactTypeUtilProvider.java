package io.apicurio.registry.types.provider;

import io.apicurio.registry.content.canon.ContentCanonicalizer;
import io.apicurio.registry.content.canon.JsonContentCanonicalizer;
import io.apicurio.registry.content.dereference.ContentDereferencer;
import io.apicurio.registry.content.extract.ContentExtractor;
import io.apicurio.registry.content.extract.NoopContentExtractor;
import io.apicurio.registry.content.refs.NoOpReferenceFinder;
import io.apicurio.registry.content.refs.ReferenceFinder;
import io.apicurio.registry.rules.compatibility.BigqueryCompatibilityChecker;
import io.apicurio.registry.rules.compatibility.CompatibilityChecker;
import io.apicurio.registry.rules.validity.BigqueryContentValidator;
import io.apicurio.registry.rules.validity.ContentValidator;

public class BigQueryArtifactTypeUtilProvider extends AbstractArtifactTypeUtilProvider {
    @Override
    protected CompatibilityChecker createCompatibilityChecker() {
        return new BigqueryCompatibilityChecker();
    }

    @Override
    protected ContentCanonicalizer createContentCanonicalizer() {
        return new JsonContentCanonicalizer();
    }

    @Override
    protected ContentValidator createContentValidator() {
        return new BigqueryContentValidator();
    }

    @Override
    protected ContentExtractor createContentExtractor() {
        // A big query schema does not contain metadata.
        return NoopContentExtractor.INSTANCE;
    }

    @Override
    public String getArtifactType() {
        return "BIGQUERY";
    }

    @Override
    public ContentDereferencer getContentDereferencer() {
        return null;
    }

    /**
     * A bigquery schema does not contain references.
     * @return a NoOpReferenceFinder
     */
    @Override
    public ReferenceFinder getReferenceFinder() {
        return NoOpReferenceFinder.INSTANCE;
    }
}
