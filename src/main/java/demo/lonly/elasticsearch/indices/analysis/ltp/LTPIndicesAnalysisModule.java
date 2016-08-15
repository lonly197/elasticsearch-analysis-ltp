package demo.lonly.elasticsearch.indices.analysis.ltp;

import org.elasticsearch.common.inject.AbstractModule;

public class LTPIndicesAnalysisModule extends AbstractModule {

    @Override
    protected void configure() {
        //使用单例状态-饿汉模式加载
        bind(LTPIndicesAnalysis.class).asEagerSingleton();
    }

}
