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
 * 插件的主入口，与ElasticSearch插件关联
 * 1、继承AbstractPlugin
 * 2、定义插件名称，方便在ElasticSearch配置文件里指定插件使用
 * 3、定义一个插件描述，然后将ltp绑定处理器加入ElasticSearch的分析模块
 * Created by Lonly on 2016/7/18.
 */
public class LTPAnalysisPlugin extends Plugin {

    public final static String PLUGIN_NAME = "ltp-analysis";

    /**
     * 系统配置项
     */
    private final Settings settings;

    public LTPAnalysisPlugin(Settings settings) {
        this.settings = settings;
    }

    /**
     * 插件名称
     * @return
     */
    @Override
    public String name() {
        return PLUGIN_NAME;
    }

    /**
     * 插件描述
     * @return
     */
    @Override
    public String description() {
        return "LTP analysis plugin for elasticsearch.";
    }

    @Override
    public Collection<Module> nodeModules() {
        //创建自己的模块集合
        return Collections.<Module> singletonList(new LTPIndicesAnalysisModule());
    }

    public void onModule(AnalysisModule module) {
        module.addProcessor(new LTPAnalysisBinderProcessor());
    }
}
