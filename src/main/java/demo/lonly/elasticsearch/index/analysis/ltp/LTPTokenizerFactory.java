package demo.lonly.elasticsearch.index.analysis.ltp;

import com.mashape.unirest.http.exceptions.UnirestException;
import demo.lonly.stp.cfg.Configuration;
import demo.lonly.stp.lucene.LTPTokenizer;
import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractTokenizerFactory;
import org.elasticsearch.index.settings.IndexSettingsService;
import org.json.JSONException;

import java.io.IOException;

public class LTPTokenizerFactory extends AbstractTokenizerFactory {
    private final Settings settings;
    private String TAG_URL;

    // The name is associate with this class, which will be
    // called in BinderProcesser
    public static final String NAME = "ltp";

    @Inject
    public LTPTokenizerFactory(Index index, IndexSettingsService indexSettingsService, Environment env, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettingsService.getSettings(), name, settings);
        this.settings = settings;
        // 插件私有配置初始化
        //Configuration.init(settings,env);
    }

    @Override
    public Tokenizer create() {
        //TAG_URL = settings.get("TAG_URL", "").toString();
        LTPTokenizer tokenizer = null;


        try {
            tokenizer = new LTPTokenizer();
        } catch (IOException | JSONException | UnirestException e) {
            e.printStackTrace();
        }
        return tokenizer;
    }

}
