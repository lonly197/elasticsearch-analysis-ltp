/**
 * 玻森数据 中文分词 版本 0.8.2
 *
 */
package demo.lonly.stp.lucene;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.json.JSONException;

import java.io.IOException;


/**
 * Implementation of ltp word segmenter
 * on Lucene Analyzer interface
 */
public final class LTPAnalyzer extends Analyzer {

    private String TAG_URL;

    public LTPAnalyzer(String URL){
    	super();
        this.TAG_URL = URL;
    }
    
    @Override
    protected TokenStreamComponents createComponents(String fieldName){
        Tokenizer BTokenizer = null;
        try {

            BTokenizer = new LTPTokenizer(TAG_URL);
        } catch (IOException | JSONException | UnirestException e) {
            e.printStackTrace();
        }
        return new TokenStreamComponents(BTokenizer);
    }
   
}
