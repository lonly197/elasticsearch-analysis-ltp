package demo.lonly.elasticsearch.plugin.analysis.ltp;

import demo.lonly.elasticsearch.index.analysis.ltp.LTPAnalysisBinderProcessor;
import demo.lonly.elasticsearch.indices.analysis.ltp.LTPIndicesAnalysisModule;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.plugins.Plugin;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by Lonly on 2016/7/18.
 */
public class LTPAnalysisPlugin extends Plugin {

    public static String PLUGIN_NAME = "ltp-analysis";

    private final Settings settings;

    public LTPAnalysisPlugin(Settings settings) {
        this.settings = settings;
    }

    @Override
    public String name() {
        return PLUGIN_NAME;
    }

    @Override
    public String description() {
        return "LTP analysis plugin for elasticsearch.";
    }

    @Override
    public Collection<Module> nodeModules() {
        return Collections.<Module> singletonList(new LTPIndicesAnalysisModule());
    }

    public void onModule(AnalysisModule module) {
        module.addProcessor(new LTPAnalysisBinderProcessor());
    }
}
