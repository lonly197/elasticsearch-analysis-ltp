package demo.lonly.elasticsearch.index.analysis.ltp;

import com.mashape.unirest.http.exceptions.UnirestException;
import demo.lonly.stp.lucene.LTPTokenizer;
import org.apache.lucene.analysis.Tokenizer;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
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
    public LTPTokenizerFactory(Index index, IndexSettingsService indexSettingsService, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettingsService.getSettings(), name, settings);
        this.settings = settings;
    }

    @Override
    public Tokenizer create() {
        TAG_URL = settings.get("TAG_URL", "").toString();
        LTPTokenizer BTokenizer = null;


        try {
            BTokenizer = new LTPTokenizer(TAG_URL);
        } catch (IOException | JSONException | UnirestException e) {
            e.printStackTrace();
        }
        return BTokenizer;
    }

}
