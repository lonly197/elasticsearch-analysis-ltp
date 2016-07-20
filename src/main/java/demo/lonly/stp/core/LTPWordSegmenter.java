/**
 * ltp word segmenter beta 1.0.0
 * LTP中文分词 版本 1.0.0
 */
package demo.lonly.stp.core;

import com.mashape.unirest.http.exceptions.UnirestException;
import demo.lonly.stp.util.RequestUtil;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.ESLoggerFactory;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public final class LTPWordSegmenter {

    private String TAG_URL;

    private List<String> words = new ArrayList<String>();
    private Iterator<String> wordsIter = Collections.emptyIterator();
    private Reader input;

    private ESLogger logger = ESLoggerFactory.getLogger("ltp plugin");

    public LTPWordSegmenter(Reader input, String URL)
            throws IOException, JSONException, UnirestException {
        this.input = input;
        logger.info("TAG_URL:"+URL);
        //this.TAG_URL = "http://192.1.1.114:8118/segment";
        this.TAG_URL = URL;
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
        // Get the new word token of target
        URL url = new URL(TAG_URL.trim() + "?content="
                + URLEncoder.encode(target.trim(), "UTF-8"));
        logger.info("getSegemnts-Request:" + TAG_URL.trim() + "?content=" + target.trim());
        String res = RequestUtil.doRequest(url);

        makeToken(res);
    }

    /**
     * Get the token result from LTP word segmenter.
     *
     * @param jn
     */
    private void makeToken(String jn) {
        try {
            // Get Json-array as it encoded before
            List<String> jaTemp = Arrays.asList(jn.replaceAll("\"", "").replace("\n", "").split(" "));
            if (jaTemp.size() > 0) {
                jaTemp.forEach(item -> {
                    this.words.add(item);
                });
            } else {
                logger.info("No string input", jaTemp);
            }

        } catch (Exception e) {
            logger.error("Exception", e, e);
            throw new RuntimeException("Exception");
        } finally {
            // Assign to words iterator
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
