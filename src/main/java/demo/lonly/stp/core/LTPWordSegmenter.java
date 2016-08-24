/**
 * ltp word segmenter beta 1.0.0
 * LTP中文分词 版本 1.0.0
 */
package demo.lonly.stp.core;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.ESLoggerFactory;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * LTP分词器
 */
public final class LTPWordSegmenter {

    private static ESLogger logger = ESLoggerFactory.getLogger("LTPWordSegmenter");

    private static ISegmenter segmenter = new LocalSegmenter();

    private List<String> words = new ArrayList<String>();
    private Iterator<String> wordsIter = Collections.emptyIterator();
    private Reader input;

    public LTPWordSegmenter(Reader input) {
        this.input = input;
        /*if(Configuration.getIsLocal()){
            // JNI方式调用本地分词模型
            //segmenter = LocalSegmenterFactory.build(Configuration.getModelPath());
            segmenter = new LocalSegmenter();
        }else{
            // Restful方式调用分词服务
            segmenter = new RemoteSegmenter(Configuration.getApiUrl());
        }*/

    }

    /**
     * Get the input string
     *
     * @param input
     * @return
     * @throws IOException
     */
    public String getStringText(Reader input) throws IOException {
        StringBuffer target = new StringBuffer();
        try (BufferedReader br = new BufferedReader(input)) {
            String temp;
            while ((temp = br.readLine()) != null) {
                target.append(temp + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return target.toString();
    }

    /**
     * Call LTP word segmenter API via Java library Unirest.
     *
     * @param target, the text to be processed
     * @throws JSONException
     * @throws UnirestException
     * @throws IOException
     */
    public void segment(String target) throws JSONException, UnirestException, IOException {
        // Clean the word token
        this.words.clear();
        try {
            this.words = segmenter.segment(target);
        } catch (Exception e) {
            logger.error("LTP Segmenter Error", e);
        } finally {
            this.wordsIter = this.words.iterator();
        }



    }

    public void reset(Reader input) throws IOException, JSONException, UnirestException {
        // Reset input
        setInput(input);
        String target = getStringText(input);
        // Do segmentation
        segment(target);
    }

    public Reader getInput() {
        return input;
    }

    public void setInput(Reader input) {
        this.input = input;
    }

    public Iterator<String> getWordsIter() {
        return this.wordsIter;
    }
}
