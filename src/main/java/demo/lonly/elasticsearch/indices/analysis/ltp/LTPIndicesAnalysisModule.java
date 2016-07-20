package demo.lonly.elasticsearch.indices.analysis.ltp;

import org.elasticsearch.common.inject.AbstractModule;

public class LTPIndicesAnalysisModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(LTPIndicesAnalysis.class).asEagerSingleton();

    }

}
