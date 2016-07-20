package demo.lonly.stp.core;

import demo.lonly.stp.util.RequestUtil;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.ESLoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lonly on 2016/7/21.
 */
public class RemoteSegmenter implements ISegmenter {

    private ESLogger logger = ESLoggerFactory.getLogger("RemoteSegmenter");

    private String tag_url;
    private List<String> words = new ArrayList<String>();

    public RemoteSegmenter(String url) {
        this.tag_url = url;
    }

    @Override
    public List<String> segment(String target) {
        // Clean the word token
        this.words.clear();
        // Get the new word token of target
        URL url = null;
        try {
            url = new URL(this.tag_url.trim() + "?content="
                    + URLEncoder.encode(target.trim(), "UTF-8"));
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        String res = RequestUtil.doRequest(url);
        makeToken(res);
        return this.words;
    }

    private void makeToken(String str) {
        try {
            // Get Json-array as it encoded before
            List<String> jaTemp = Arrays.asList(str.replaceAll("\"", "").replace("\n", "").split(" "));
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
        }
    }
}
